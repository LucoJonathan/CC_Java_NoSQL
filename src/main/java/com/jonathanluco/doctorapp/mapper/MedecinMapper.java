package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.model.Medecin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedecinMapper {

    MedecinDTO toDto(Medecin medecin);

    Medecin toModel(MedecinDTO medecinDTO);

    @Mapping(target = "matricule", ignore = true)
    void updateModel(@MappingTarget Medecin medecin, MedecinDTO medecinDTO);
}
