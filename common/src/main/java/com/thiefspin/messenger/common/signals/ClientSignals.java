package com.thiefspin.messenger.common.signals;

import lombok.Getter;

@Getter
public enum ClientSignals {
    DISCONNECT("Disconnect"),
    CONSUME("Consume"),
    PRODUCE("Produce");

    private final String name;

    ClientSignals(String name) {
        this.name = name;
    }
}
