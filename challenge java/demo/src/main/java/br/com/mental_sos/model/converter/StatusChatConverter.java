package br.com.mental_sos.model.converter;

import br.com.mental_sos.model.enuns.StatusChat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusChatConverter implements AttributeConverter<StatusChat, Short> {
    @Override
    public Short convertToDatabaseColumn(StatusChat attribute) {
        if (attribute == null) return null;
        return (short) attribute.getValor();
    }

    @Override
    public StatusChat convertToEntityAttribute(Short dbData) {
        if (dbData == null) return null;
        return StatusChat.fromValor(dbData);
    }
}