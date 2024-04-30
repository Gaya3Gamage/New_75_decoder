package com.decoder.decoder.Configuration;
import com.decoder.decoder.Decoder;
import com.decoder.decoder.MessageDecoder;
import com.decoder.decoder.Service.MessageRecordService;
import com.decoder.decoder.repo.SongMaoMessageRecordRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {

    @Bean
    public Decoder decoder(MessageRecordService messageRecordService) {
        return new Decoder(messageRecordService);
    }

    @Bean
    public MessageRecordService messageRecordService(SongMaoMessageRecordRepo messageRecordRepo) {
        return new MessageRecordService(messageRecordRepo);
    }

}
