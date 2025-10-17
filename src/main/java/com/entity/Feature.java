package com.entity;

import lombok.Getter;

@Getter
public enum Feature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String value;

    Feature(String value) {
        this.value = value;
    }

    public static Feature fromValue(String value) {
        for (Feature f : values()) {
            if (f.getValue().equals(value)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Unknown feature: " + value);
    }
}
