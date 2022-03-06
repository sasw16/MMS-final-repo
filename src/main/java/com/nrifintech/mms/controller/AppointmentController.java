package com.nrifintech.mms.controller;

import java.time.LocalDate;

import java.util.List;
import java.time.temporal.ChronoUnit;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import com.nrifintech.mms.entity.Appointment;
import com.nrifintech.mms.entity.Doctor;
import com.nrifintech.mms.entity.Patient;
import com.nrifintech.mms.service.AppointmentService;
import com.nrifintech.mms.service.DoctorService;

@SessionAttributes(value = { "user" })
@Controller
public class AppointmentController {

	@Autowired
	private DoctorService doctorService;
	@Autowired
	private Doctor doctor;
	@Autowired
	private Appointment appointment;
	@Autowired
	private AppointmentService appointmentService;
	
	@RequestMapping("/cancelappointment")
	public ModelAndView cancelAppointment(@RequestParam("ap_id")Integer ap_id, ModelMap map,Model model,HttpSession session) {
		boolean check = this.appointmentService.checkForCancel(ap_id);
		ModelAndView m = new ModelAndView("patDash");
		if(check == true) {
			
			Patient p = (Patient)session.getAttribute("user");
			List<Appointment> appointmentByPId = this.appointmentService.getAppointmentByPId(p.getPid());
			model.addAttribute("allAppointments", appointmentByPId);
			map.put("success", "true");
			return m;
		}
		else {
			Patient p = (Patient)session.getAttribute("user");
			List<Appointment> appointmentByPId = this.appointmentService.getAppointmentByPId(p.getPid());
			model.addAttribute("allAppointments", appointmentByPId);
			map.put("error", "true");
			return m;
		}
	}
	
	
	

	@RequestMapping("/filterdoctor")
	public ModelAndView filterDoctor(@RequestParam("specialisation") String specialisation, Model model) {
		ModelAndView m = new ModelAndView("addappointment");
		List<Doctor> filtered = this.doctorService.filterDoctorForPatient(specialisation);
		model.addAttribute("allDoctors", filtered);

		return m;
	}

	@RequestMapping("/bookappointment")
	public ModelAndView appointmentForm(@RequestParam("doc_id") Integer doc_id, HttpSession session, Model model,
			ModelMap map) {
		ModelAndView m = new ModelAndView("bookappointment");
		doctor = this.doctorService.getDoctorById(doc_id);
		// Patient p = (Patient)session.getAttribute("user");
		model.addAttribute("doctor", doctor);
		return m;
	}

	@RequestMapping("/processappointment")
	public ModelAndView processAppointment(@RequestParam("doc_id") Integer doc_id, @RequestParam("slot") String slot,
			@RequestParam("status") String status, @RequestParam("date") String date, HttpSession session, Model model,
			ModelMap map) {
		ModelAndView m = new ModelAndView("bookappointment");
		doctor = this.doctorService.getDoctorById(doc_id);
		Patient p = (Patient) session.getAttribute("user");
		System.out.println(doctor);
		System.out.println(p);
		LocalDate ld = LocalDate.now();
		System.out.println(ld);
		LocalDate d = LocalDate.parse(date);
		System.out.println(d);
		//Period period = Period.between(ld, d);

		
		
		long days = ChronoUnit.DAYS.between(ld, d);
		System.out.println(days);
		
		
		//if (period.getDays() <= 0 || period.getDays() > 7) {
			
			if (days <= 0 || days > 7) {
			map.put("error", "true");
			// model.addAttribute("patient", p);
			model.addAttribute("doctor", doctor);
			return m;
		} else {
			appointment.setDate(date);
			appointment.setDoc_id(doc_id);
			appointment.setSpecialisation(doctor.getSpecialisation());
			appointment.setP_id(p.getPid());
			appointment.setDoctorName(doctor.getName());
			appointment.setPatientName(p.getName());
			appointment.setSlot(slot);
			appointment.setStatus(status);
			appointment.setFees(doctor.getFees());
			appointment.setCancel("Not Cancelled");
			if (this.appointmentService.verifyAppointment(appointment)==1) {
				System.out.println(("abcdsuccess"));
				map.put("success", "true");
				model.addAttribute("doctor", doctor);
				return m;
			} else if(this.appointmentService.verifyAppointment(appointment)==0) {
				model.addAttribute("doctor", doctor);
				map.put("error1", "true");//you already have an appointment
				return m;
			}
			else{
				System.out.println(("abcd"));
				model.addAttribute("doctor", doctor);
				map.put("error2", "true");//slot not available
				return m;
			}

		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}