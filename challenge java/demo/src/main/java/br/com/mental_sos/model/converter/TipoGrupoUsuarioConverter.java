package br.com.mental_sos.model.converter;

import br.com.mental_sos.model.enuns.TipoGrupoUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoGrupoUsuarioConverter implements AttributeConverter<TipoGrupoUsuario, Short> {
    @Override
    public Short convertToDatabaseColumn(TipoGrupoUsuario attribute) {
        if (attribute == null) return null;
        return (short) attribute.getValor();
    }

    @Override
    public TipoGrupoUsuario convertToEntityAttribute(Short dbData) {
        if (dbData == null) return null;
        return TipoGrupoUsuario.fromValor(dbData);
    }
}