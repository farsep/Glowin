package com.glowin.converter;

import com.glowin.models.enums.Rol;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolConverter implements AttributeConverter<Rol, String> {

    @Override
    public String convertToDatabaseColumn(Rol rol) {
        if (rol == null) {
            return null;
        }
        return rol.name();
    }

    @Override
    public Rol convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rol.valueOf(dbData);
    }
}