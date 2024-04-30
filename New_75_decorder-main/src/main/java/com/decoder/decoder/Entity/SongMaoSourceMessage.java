package com.decoder.decoder.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Entity
@NoArgsConstructor
@Table
@Data
public class SongMaoSourceMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    @Column(columnDefinition = "text")
    private String message;

    public SongMaoSourceMessage(String msgStr) {
        message = msgStr;
    }
}
