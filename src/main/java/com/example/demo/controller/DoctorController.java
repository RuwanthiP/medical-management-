package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Appointment;
import com.example.demo.model.Patient;
import com.example.demo.repository.DoctorRepository;
import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;

@Controller
@RequestMapping("/doctors/")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	@Autowired
	private DoctorRepository doctorRepository;

	// display list of doctors
	@GetMapping("list")
	public String viewHomePage(Model model) {
		return findPaginated(1, "fullName", "asc", model);
	}
	
	@GetMapping("showNewDoctorForm")
	public String showNewDoctorForm(Model model) {
		// create model attribute to bind form data
		Doctor doctor = new Doctor();
		model.addAttribute("doctor", doctor);
		return "new_doctor";
	}
	
	@PostMapping("saveDoctor")
	public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
		// save doctor to database
		doctorService.saveDoctor(doctor);
		return "redirect:/doctors/list";
	}
	
	@GetMapping("showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get doctor from the service
		Doctor doctor = doctorService.getDoctorById(id);
		
		// set doctor as a model attribute to pre-populate the form
		model.addAttribute("doctor", doctor);
		return "update_doctor";
	}
	
	@GetMapping("deleteDoctor/{id}")
	public String deleteDoctor(@PathVariable (value = "id") long id) {
		
		// call delete doctor method
		this.doctorService.deleteDoctorById(id);
		return "redirect:/doctors/list";
	}

	@GetMapping("showChannelDoctorForm/{id}")
	public String showChannelDoctorForm(@PathVariable ( value = "id") long id, Model model) {
		// create model attribute to bind form data
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		return "new_appointment";
	}

	@PostMapping("update/{id}")
	public String updateDoctor(@PathVariable("id") long id, Doctor doctor, BindingResult result, Model model) {
		if(result.hasErrors()) {
			doctor.setId(id);
			return "update_doctor";
		}

		// update doctors
		doctorRepository.save(doctor);

		return "redirect:/doctors/list";
	}

	@GetMapping("page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 15;

		Page<Doctor> page = doctorService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Doctor> listdoctors = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listDoctors", listdoctors);
		return "index_doctor";
	}
}
