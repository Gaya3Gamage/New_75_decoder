package com.decoder.decoder.Service;

import com.decoder.decoder.Entity.SongMaoMessageRecord;
import com.decoder.decoder.MessageDecoder;
import com.decoder.decoder.repo.SongMaoMessageRecordRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MessageRecordService {

    private final SongMaoMessageRecordRepo messageRecordRepo;

    @Autowired
    public MessageRecordService(SongMaoMessageRecordRepo messageRecordRepo) {
        this.messageRecordRepo = messageRecordRepo;
    }

    public void saveMessageRecord(SongMaoMessageRecord messageRecord) {
        try {
            messageRecordRepo.save(messageRecord);
            log.info("Message record saved successfully: {}", messageRecord);
        } catch (Exception e) {
            log.error("Failed to save message record: {}", messageRecord, e);
            throw e;
        }
    }
}





