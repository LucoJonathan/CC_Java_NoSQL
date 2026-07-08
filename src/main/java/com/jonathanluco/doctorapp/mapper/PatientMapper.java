package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.model.Patient;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);

    default Page<PatientDTO> toDtoPage(Page<Patient> patientsPage) {
        return patientsPage.map(this::toDto);
    }

    Patient toModel(PatientDTO patientDTO);

    @Mapping(target = "numeroSS", ignore = true)
    void updateModel(@MappingTarget Patient patient, PatientDTO patientDTO);
}
