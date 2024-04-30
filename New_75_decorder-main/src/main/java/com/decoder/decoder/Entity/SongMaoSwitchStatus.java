package com.decoder.decoder.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@Table
public class SongMaoSwitchStatus {

    public SongMaoSwitchStatus(String name, int status, Date time) {
        this.name = name;
        this.status = status;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private int status;
    private Date time;

}
