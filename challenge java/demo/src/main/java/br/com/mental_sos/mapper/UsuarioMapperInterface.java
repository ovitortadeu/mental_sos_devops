package br.com.mental_sos.mapper;

import br.com.mental_sos.dto.UsuarioCreateDTO;
import br.com.mental_sos.dto.UsuarioDTO;
import br.com.mental_sos.dto.UsuarioUpdateDTO; 
import br.com.mental_sos.model.Usuario;
import org.mapstruct.BeanMapping; 
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget; 
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapperInterface {
    @Mapping(source = "grupoUsuario.tipoGrupo", target = "tipoGrupo")
    UsuarioDTO toUsuarioDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "grupoUsuario", ignore = true)
    @Mapping(target = "chatsCriados", ignore = true)
    @Mapping(target = "mensagensEnviadas", ignore = true)
    @Mapping(target = "mensagensComoPaciente", ignore = true)
    @Mapping(target = "mensagensComoPsicologo", ignore = true)
    @Mapping(target = "ongsAdministradas", ignore = true)
    Usuario toUsuario(UsuarioCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsuarioFromDto(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);
}