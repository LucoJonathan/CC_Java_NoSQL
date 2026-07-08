package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.PrescriptionDTO;
import com.jonathanluco.doctorapp.model.Prescription;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper MapStruct entre Prescription et PrescriptionDTO.
 */
@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    PrescriptionDTO toDto(Prescription prescription);

    Prescription toModel(PrescriptionDTO prescriptionDTO);

    List<PrescriptionDTO> toDtoList(List<Prescription> prescriptions);

    List<Prescription> toModelList(List<PrescriptionDTO> prescriptionDTOs);
}
