package com.person.enums;

import lombok.Getter;

@Getter
public enum RecordStatus {
    ACTIVE(0),
    DELETED(1),
    ARCHIVED(2);

    int value;

    RecordStatus(int value) {
        this.value = value;
    }
}
