package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.model.Consultation;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper MapStruct entre Consultation et ConsultationDTO.
 */
@Mapper(componentModel = "spring", uses = PrescriptionMapper.class)
public interface ConsultationMapper {

    @Mapping(target = "patientNumeroSS", source = "patientAssiste.numeroSS")
    @Mapping(target = "medecinMatricule", source = "medecinDonne.matricule")
    ConsultationDTO toDto(Consultation consultation);

    List<ConsultationDTO> toDtoList(List<Consultation> consultations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patientAssiste", source = "patient")
    @Mapping(target = "medecinDonne", source = "medecin")
    Consultation toModel(ConsultationDTO consultationDTO, Patient patient, Medecin medecin);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroConsultation", ignore = true)
    @Mapping(target = "patientAssiste", source = "patient")
    @Mapping(target = "medecinDonne", source = "medecin")
    void updateModel(ConsultationDTO consultationDTO, Patient patient, Medecin medecin, @MappingTarget Consultation consultation);
}
