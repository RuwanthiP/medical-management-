package com.example.demo.service;

import com.example.demo.model.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {
	List<Doctor> getAllDoctors();
	void saveDoctor(Doctor doctor);
	Doctor getDoctorById(long id);
	void deleteDoctorById(long id);
	Page<Doctor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
