package com.glowin.converter;

import com.glowin.models.enums.CategoriaServicioEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoriaServicioEnumConverter implements AttributeConverter<CategoriaServicioEnum, String> {

    @Override
    public String convertToDatabaseColumn(CategoriaServicioEnum categoriaServicioEnum) {
        if (categoriaServicioEnum == null) {
            return null;
        }
        return categoriaServicioEnum.name();
    }

    @Override
    public CategoriaServicioEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return CategoriaServicioEnum.valueOf(dbData);
    }
}