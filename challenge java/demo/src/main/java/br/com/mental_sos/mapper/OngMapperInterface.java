package br.com.mental_sos.mapper;

import br.com.mental_sos.dto.OngCreateUpdateDTO;
import br.com.mental_sos.dto.OngDTO;
import br.com.mental_sos.model.Ong;
import br.com.mental_sos.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OngMapperInterface {

    @Mapping(source = "administradorOng.id", target = "administradorOngId")
    @Mapping(source = "administradorOng.username", target = "administradorOngUsername")
    OngDTO toOngDTO(Ong ong);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "administradorOng", source = "administrador")
    Ong toOng(OngCreateUpdateDTO dto, Usuario administrador);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "administradorOng", source = "administrador")
    void updateOngFromDto(OngCreateUpdateDTO dto, Usuario administrador, @MappingTarget Ong ong);
}