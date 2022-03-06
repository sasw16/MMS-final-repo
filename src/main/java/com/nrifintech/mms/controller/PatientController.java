package com.nrifintech.mms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nrifintech.mms.entity.Appointment;
import com.nrifintech.mms.entity.Doctor;
import com.nrifintech.mms.entity.Patient;
import com.nrifintech.mms.service.AppointmentService;
import com.nrifintech.mms.service.DoctorService;
import com.nrifintech.mms.service.PatientServiceImpl;

@Controller
@SessionAttributes(value = { "user" })
public class PatientController {
	
	@Autowired
	private Patient patient;
	@Autowired
	private PatientServiceImpl patientServiceImpl;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private AppointmentService appointmentService;
	
	@RequestMapping("/addappointment")
	public ModelAndView addAppointment(HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return new ModelAndView("addappointment");
	}

	@RequestMapping("/patientprofile")
	public ModelAndView patientProfile(HttpSession session) {
		ModelAndView m = new ModelAndView("patientprofile");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return m;
	}
	@RequestMapping("/doctorlist")
	public ModelAndView doctorList(HttpSession session, Model model) {
		ModelAndView m = new ModelAndView("doctorlist");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		List<Doctor> lists = this.doctorService.getDoctorListForPatients();
		model.addAttribute("allDoctors", lists);
		
		return m;
	}

	@RequestMapping("/patdash")
	public ModelAndView patientDashboard(HttpSession session,Model model) {
		ModelAndView m = new ModelAndView("patDash");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		Patient p = (Patient)session.getAttribute("user");
		List<Appointment> appointmentByPId = this.appointmentService.getAppointmentByPId(p.getPid());
		model.addAttribute("allAppointments", appointmentByPId);
		return m;
	}
	@RequestMapping("/editpatient")
	public ModelAndView editPatient(HttpSession session) {
		ModelAndView m = new ModelAndView("editpatient");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return m;
	}

	@PostMapping("/editpatientdetails")
	public ModelAndView editPatient(@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("address") String address, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("newpassword") String newpassword, Model model,
			ModelMap map,HttpSession session) {
		
		ModelAndView m = new ModelAndView("editpatient");
		Patient p = (Patient)session.getAttribute("user");
		System.out.println(p);
		
		if(password.equals(p.getPassword()) && newpassword != "") {
		patient.setPid(p.getPid());
		patient.setName(name);
		patient.setEmail(email);
		patient.setPassword(newpassword);
		patient.setAddress(address);
		patient.setAge(p.getAge());
		patient.setGender(p.getGender());
		patient.setPhone(phone);
		
		System.out.println(patient);
		
		this.patientServiceImpl.updatePatient(patient);
		map.put("success", "true");
		model.addAttribute("user", patient);
		return m;
		}
		else {
			map.put("error", "true");
			return m;
		}
		
	}

}
