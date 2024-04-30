package com.decoder.decoder.repo;

import com.decoder.decoder.Entity.SongMaoMessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
@EnableJpaRepositories
public interface SongMaoMessageRecordRepo extends JpaRepository<SongMaoMessageRecord, Long> {

    SongMaoMessageRecord findFirstByOrderByInsertTimeDesc();
}
