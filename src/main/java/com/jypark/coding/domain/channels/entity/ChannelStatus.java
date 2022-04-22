package com.jypark.coding.domain.channels.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChannelStatus {
    AVAILABLE("green"),
    WARN("yellow"),
    EXCEED("red"),
;
    private String color;
}
