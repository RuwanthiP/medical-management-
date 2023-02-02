package com.example.demo.service;

import com.example.demo.model.Appointment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    void saveAppointment(Appointment appointment);
    Appointment getAppointmentById(int id);
    void deleteAppointmentById(int id);
    Page<Appointment> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}