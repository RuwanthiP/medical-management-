package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.PatientService;

@Controller

@RequestMapping("/patients/")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @GetMapping("showForm")
    public String showPatientForm(Patient patient) {
        return "add-patient";
    }

    @GetMapping("list")
    public String patients(Model model) {
        model.addAttribute("patients", this.patientRepository.findAll());
        return "index_patient";
    }


    @PostMapping("savePatient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        // save patient to database
        patientService.savePatient(patient);
        return "redirect:list";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable ("id") long id, Model model) {
        Patient patient = this.patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id : " + id));

        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @PostMapping("update/{id}")
    public String updatePatient(@PathVariable("id") long id, Patient patient, BindingResult result, Model model) {
        if(result.hasErrors()) {
            patient.setId(id);
            return "update-patient";
        }

        // update patient
        patientRepository.save(patient);

        return "redirect:/patients/list";
    }

    @GetMapping("delete/{id}")
    public String deletePatient(@PathVariable ("id") long id, Model model) {

        Patient patient = this.patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id : " + id));

        this.patientRepository.delete(patient);
        model.addAttribute("patients", this.patientRepository.findAll());
        return "index_patient";

    }
}