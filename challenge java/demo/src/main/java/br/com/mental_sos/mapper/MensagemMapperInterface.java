package br.com.mental_sos.mapper;

import br.com.mental_sos.dto.MensagemDTO;
import br.com.mental_sos.model.Mensagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MensagemMapperInterface {
    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "remetente.id", target = "remetenteId")
    @Mapping(source = "remetente.username", target = "remetenteUsername")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "psicologo.id", target = "psicologoId")
    MensagemDTO toMensagemDTO(Mensagem mensagem);
}