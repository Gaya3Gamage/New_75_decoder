package com.decoder.decoder.repo;

import com.decoder.decoder.Entity.SongMaoMessageRecord;
import com.decoder.decoder.Entity.SongMaoSourceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface SongMaoSourceMessageRepo extends JpaRepository<SongMaoSourceMessage,Long> {

}
