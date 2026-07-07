package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.model.Medicament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicamentMapper {

    MedicamentDTO toDto(Medicament medicament);

    Medicament toModel(MedicamentDTO medicamentDTO);

    @Mapping(target = "code", ignore = true)
    void updateModel(@MappingTarget Medicament medicament, MedicamentDTO medicamentDTO);
}
