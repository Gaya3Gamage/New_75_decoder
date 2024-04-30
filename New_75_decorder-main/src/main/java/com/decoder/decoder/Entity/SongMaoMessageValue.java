package com.decoder.decoder.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table
public class SongMaoMessageValue {

    public SongMaoMessageValue(String type, double value, Date time) {
        this.type = type;
        this.value = value;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String type;
    private double value;
    private Date time;
}