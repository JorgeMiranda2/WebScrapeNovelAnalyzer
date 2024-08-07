package com.SeriesAnalyzer.main.Models.Series;

import lombok.Getter;

@Getter
public enum WorkType {
    ANIME("anime"),
    MANGA("manga"),
    NOVEL("novel");

    private final String discriminatorValue;

    WorkType(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

    public static WorkType fromDiscriminatorValue(String discriminatorValue) {
        for (WorkType workType : WorkType.values()) {
            if (workType.getDiscriminatorValue().equals(discriminatorValue)) {
                return workType;
            }
        }
        throw new IllegalArgumentException("Unknown discriminator value: " + discriminatorValue);
    }
}
