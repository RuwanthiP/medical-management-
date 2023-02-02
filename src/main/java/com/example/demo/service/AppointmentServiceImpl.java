package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        this.appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(int id) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        Appointment appointment = null;
        if (optional.isPresent()) {
            appointment = optional.get();
        } else {
            throw new RuntimeException(" Appointment not found for id :: " + id);
        }
        return appointment;
    }

    @Override
    public void deleteAppointmentById(int id) {
        this.appointmentRepository.deleteById(id);
    }

    @Override
    public Page<Appointment> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.appointmentRepository.findAll(pageable);
    }
}
