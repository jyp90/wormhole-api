package com.jypark.coding.domain.channels.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "channelId")
public class Channel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long channelId;

    private String name;

    private String url;

    private Integer permitCount;

    private boolean available;

    @Enumerated(EnumType.STRING)
    private ChannelStatus status;
}
