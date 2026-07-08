package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.model.Medecin;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedecinMapper {

    MedecinDTO toDto(Medecin medecin);

    default Page<MedecinDTO> toDtoPage(Page<Medecin> medecinsPage) {
        return medecinsPage.map(this::toDto);
    }

    Medecin toModel(MedecinDTO medecinDTO);

    @Mapping(target = "matricule", ignore = true)
    void updateModel(@MappingTarget Medecin medecin, MedecinDTO medecinDTO);
}
