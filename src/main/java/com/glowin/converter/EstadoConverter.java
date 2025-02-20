package com.glowin.converter;

import com.glowin.models.enums.Estado;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, String> {

    @Override
    public String convertToDatabaseColumn(Estado estado) {
        if (estado == null) {
            return null;
        }
        return estado.name();
    }

    @Override
    public Estado convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Estado.valueOf(dbData);
    }
}