package com.jonathanluco.doctorapp.mapper;

import com.jonathanluco.doctorapp.dto.*;
import com.jonathanluco.doctorapp.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Mapper Tests")
class MapperTest {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private MedecinMapper medecinMapper;

    @Autowired
    private MedicamentMapper medicamentMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Test
    void patientMapperShouldMapBothWays() {
        Patient patient = new Patient("123", "Jean", "j@example.com", "jean", "pwd");
        PatientDTO dto = patientMapper.toDto(patient);
        assertEquals("123", dto.getNumeroSS());

        Patient mapped = patientMapper.toModel(dto);
        assertEquals("123", mapped.getNumeroSS());
    }

    @Test
    void medecinMapperShouldMapBothWays() {
        Medecin medecin = new Medecin("DOC1", "Dr", "d@example.com", "dr", "pwd");
        MedecinDTO dto = medecinMapper.toDto(medecin);
        assertEquals("DOC1", dto.getMatricule());

        Medecin mapped = medecinMapper.toModel(dto);
        assertEquals("DOC1", mapped.getMatricule());
    }

    @Test
    void medicamentMapperShouldMapBothWays() {
        Medicament medicament = new Medicament("MED1", "Aspirin");
        MedicamentDTO dto = medicamentMapper.toDto(medicament);
        assertEquals("MED1", dto.getCode());

        Medicament mapped = medicamentMapper.toModel(dto);
        assertEquals("MED1", mapped.getCode());
    }

    @Test
    void prescriptionMapperShouldMapBothWays() {
        Prescription prescription = new Prescription("MED1", 3);
        PrescriptionDTO dto = prescriptionMapper.toDto(prescription);
        assertEquals("MED1", dto.getCodeMedicament());

        Prescription mapped = prescriptionMapper.toModel(dto);
        assertEquals(3, mapped.getNbPrise());
    }

    @Test
    void consultationMapperShouldMapBothWays() {
        Patient patient = new Patient("123", "Jean", "j@example.com", "jean", "pwd");
        Medecin medecin = new Medecin("DOC1", "Dr", "d@example.com", "dr", "pwd");
        Consultation consultation = new Consultation("C1", LocalDateTime.now());
        consultation.setPatientAssiste(patient);
        consultation.setMedecinDonne(medecin);
        consultation.addPrescription(new Prescription("MED1", 3));

        ConsultationDTO dto = consultationMapper.toDto(consultation);
        assertEquals("C1", dto.getNumeroConsultation());
        assertEquals("123", dto.getPatientNumeroSS());
        assertEquals(1, dto.getPrescriptions().size());

        Consultation mapped = consultationMapper.toModel(dto, patient, medecin);
        assertEquals("C1", mapped.getNumeroConsultation());
    }

    @Test
    void pageMappersShouldMapPages() {
        Patient patient = new Patient("123", "Jean", "j@example.com", "jean", "pwd");
        assertEquals(1, patientMapper.toDtoPage(new PageImpl<>(List.of(patient), PageRequest.of(0, 1), 1)).getContent().size());

        Medecin medecin = new Medecin("DOC1", "Dr", "d@example.com", "dr", "pwd");
        assertEquals(1, medecinMapper.toDtoPage(new PageImpl<>(List.of(medecin), PageRequest.of(0, 1), 1)).getContent().size());
    }
}
