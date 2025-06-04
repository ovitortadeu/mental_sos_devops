package br.com.mental_sos.mapper;

import br.com.mental_sos.dto.ChatDTO;
import br.com.mental_sos.model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapperInterface {
    @Mapping(source = "criador.id", target = "criadorId")
    @Mapping(source = "criador.username", target = "criadorUsername")
    ChatDTO toChatDTO(Chat chat);
}