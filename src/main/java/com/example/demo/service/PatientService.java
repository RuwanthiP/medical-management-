package com.example.demo.service;

import com.example.demo.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    void savePatient(Patient patient);
    Patient getPatientById(long id);
    void deletePatientById(long id);
//    Page<Patient> findPaginated();
}

