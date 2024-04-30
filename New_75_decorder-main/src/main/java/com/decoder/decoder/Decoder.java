package com.decoder.decoder;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.decoder.decoder.Entity.SongMaoMessageRecord;
import com.decoder.decoder.Entity.SongMaoMessageValue;
import com.decoder.decoder.Entity.SongMaoSourceMessage;
import com.decoder.decoder.Entity.SongMaoSwitchStatus;
import com.decoder.decoder.Service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static cn.hutool.core.date.DateUtil.offsetMinute;
import java.util.*;

@Slf4j
@Component
@Transactional
public class Decoder implements MessageDecoder{
    private final static String FIELD_SPLIT = ";";
    private final static String KV_SPLIT = ":";

    private final static String VALUES_SPLIT = "\\|";

    private final static int VALUE_START_INDEX = 7;

    private final static String VALUE_END_CHAR = "K";
    private final MessageRecordService messageRecordService;

    public Decoder(MessageRecordService messageRecordService) {
        this.messageRecordService = messageRecordService;
    }



    @Override
    public void decode(String msgStr) {
        log.info("Server get songMao message: {}", msgStr);

        SongMaoSourceMessage sourceMessage = new SongMaoSourceMessage(msgStr);
        SongMaoMessageRecord messageRecord = new SongMaoMessageRecord();
        messageRecord.setSourceMessage(sourceMessage);

        String[] list = msgStr.split(FIELD_SPLIT);
        log.info("split string: {}", list[2]);

        if (list.length == 0) {
            return;
        }

        try {
            // Create and populate the SongMaoMessageRecord entity
            messageRecord.setLen(Integer.parseInt(list[0].split(KV_SPLIT)[1]));
            messageRecord.setMachineId(list[1]);
            DateTime startTime = DateUtil.parse(
                    list[2].substring(list[2].indexOf(KV_SPLIT) + 1), "dd/MM/yyyy,HH:mm:ss");
            messageRecord.setStartTime(startTime.toJdkDate());
            messageRecord.setInsertTime(DateUtil.date());
            messageRecord.setVoltage((float) Double.parseDouble(list[3].split(KV_SPLIT)[1])); // Casting to float
            // Using Double.parseDouble()

            int interval;
            switch (Integer.parseInt(list[4].split(KV_SPLIT)[1])) {
                case 0:
                    messageRecord.setInterval("No_Save");
                    interval = 0;
                    break;
                case 1:
                    messageRecord.setInterval("5min");
                    interval = 5;
                    break;
                case 2:
                    messageRecord.setInterval("15min");
                    interval = 15;
                    break;
                case 3:
                    messageRecord.setInterval("30min");
                    interval = 30;
                    break;
                case 4:
                    messageRecord.setInterval("60min");
                    interval = 60;
                    break;
                case 5:
                    messageRecord.setInterval("2hour");
                    interval = 120;
                    break;
                default:
                    messageRecord.setInterval("Error interval id");
                    interval = -1;
            }
            messageRecord.setMessageCount(Integer.parseInt(list[5].split(KV_SPLIT)[1]));
            messageRecord.setMessageIndex(Integer.parseInt(list[6].split(KV_SPLIT)[1]));

            List<Date> timeList = new ArrayList<>(messageRecord.getMessageCount());
            for (int i = 0; i < messageRecord.getMessageCount(); i++) {
                timeList.add(offsetMinute(startTime, i * interval));
            }
            int index = decodeMessageValues(list, timeList, messageRecord);
            messageRecord.setSwitchStatuses(decodeSwitchStatus(list, timeList, index));
            System.out.println();

            log.info("Decoded message record: {}", messageRecord);
            messageRecordService.saveMessageRecord(messageRecord);


            // Save message record to the database
            //songMaoMessageRecordRepo.save(messageRecord);
        } catch (Exception e) {
            log.error("Failed to decode message: {}", msgStr, e);
        }
    }
    private int decodeMessageValues(String[] list, List<Date> timeList,
                                    SongMaoMessageRecord messageRecord) {
        Set<SongMaoMessageValue> messageValue = new HashSet<>();
        String valueStr;
        String[] keyValues;
        String[] values;
        String key;
        int value;

        int index = VALUE_START_INDEX;
        valueStr = list[index];
        log.info("valueStr: {}", valueStr);
//        P01:-99999999|-99999999|-99999999|-99999999|000014834|000014834
        while (!valueStr.startsWith(VALUE_END_CHAR)) {
            keyValues = valueStr.split(KV_SPLIT);
            if (keyValues.length == 2) {
                key = keyValues[0];
//                P01
                values = keyValues[1].split(VALUES_SPLIT);

//      keyValue[1] = -99999999|-99999999|-99999999|-99999999|000014834|000014834
                for (int i = 0; i < values.length; i++) {
                    double doubleValue = Double.parseDouble(values[i]);
                    value = (int) doubleValue; // Casting double to int
                    if (value > 0 && value != 99999) {
                        messageValue.add(new SongMaoMessageValue(key, value, timeList.get(i)));

                    } else {
                        log.info("No data from message at index {}. Message: {}", i, keyValues);
                    }
                }
            } else {
                log.error("Decode message value fail: {}", valueStr);
            }
            index++;
            valueStr = list[index];


        }

        messageRecord.setMessageValues(messageValue);
        return index;

    }

    private Set<SongMaoSwitchStatus> decodeSwitchStatus(String[] list, List<Date> timeList, int index) {
        Set<SongMaoSwitchStatus> switchStatusesSet = new HashSet<>();

        String valueStr;
        String[] keyValues;
        String[] values;
        String key;
        int value;

        valueStr = list[index];
//        K01:99|99|99|99|00|00
        while (valueStr.contains(KV_SPLIT)) {
            keyValues = valueStr.split(KV_SPLIT);
            if (keyValues.length == 2) {
                key = keyValues[0];
//                K01
                values = keyValues[1].split(VALUES_SPLIT);
//      keyValue[1] = 99|99|99|99|00|00
                for (int i = 0; i < values.length; i++) {
                    value = Integer.parseInt(values[i]);
                    if (value != 99) {
                        switchStatusesSet.add(new SongMaoSwitchStatus(key, value, timeList.get(i)));
                    } else {
                        log.info("No data from message for switch status at index {}. Message: {}", i, keyValues);
                    }
                }
            } else {
                log.error("Decode message value fail: {}", valueStr);
            }
            index++;
            valueStr = list[index];
        }
        return switchStatusesSet;
    }

}