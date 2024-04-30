package com.decoder.decoder.Entity;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table
public class SongMaoMessageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int len;
    private String machineId;
    private Date startTime;
    private Date insertTime;
    private float voltage;
    /**
     * 上传间隔，解析为分钟数
     */
    private String interval;
    private int messageCount;
    private int messageIndex;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "source_message_id")
    private SongMaoSourceMessage sourceMessage;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Set<SongMaoMessageValue> messageValues;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Set<SongMaoSwitchStatus> switchStatuses;


}

