package com.entity;

import lombok.Getter;

@Getter
public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String label;

    Rating(String label) {
        this.label = label;
    }

    public static Rating fromValue(String value) {
        for (Rating r : Rating.values()) {
            if (r.getLabel().equals(value)) return r;
        }
        throw new IllegalArgumentException("Unknown rating: " + value);
    }
}
