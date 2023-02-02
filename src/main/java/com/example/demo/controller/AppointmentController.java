package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.AppointmentRepository;

@Controller
@RequestMapping("/appointments/")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("saveAppointment")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        // save doctor to database
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments/list";
    }

    @GetMapping("list")
    public String appointments(Model model) {
        model.addAttribute("appointments", this.appointmentRepository.findAll());
        return "index_appointment";
    }
}
