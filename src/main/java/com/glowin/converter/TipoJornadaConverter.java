package com.glowin.converter;

import com.glowin.models.enums.TipoJornada;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoJornadaConverter implements AttributeConverter<TipoJornada, String> {

    @Override
    public String convertToDatabaseColumn(TipoJornada tipoJornada) {
        if (tipoJornada == null) {
            return null;
        }
        return tipoJornada.name();
    }

    @Override
    public TipoJornada convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoJornada.valueOf(dbData);
    }
}