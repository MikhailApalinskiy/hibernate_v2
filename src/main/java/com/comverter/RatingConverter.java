package com.comverter;

import com.entity.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute != null ? attribute.getLabel() : null;
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        return dbData != null ? Rating.fromValue(dbData) : null;
    }
}
