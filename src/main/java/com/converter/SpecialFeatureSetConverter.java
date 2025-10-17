package com.converter;

import com.entity.Feature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SpecialFeatureSetConverter implements AttributeConverter<Set<Feature>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Feature> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream()
                .map(Feature::getValue)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<Feature> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return Set.of();
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(String::trim)
                .map(Feature::fromValue)
                .collect(Collectors.toSet());
    }
}
