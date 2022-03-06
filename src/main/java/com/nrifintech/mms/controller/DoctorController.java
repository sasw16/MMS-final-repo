package com.nrifintech.mms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nrifintech.mms.entity.Appointment;
import com.nrifintech.mms.entity.Doctor;
import com.nrifintech.mms.service.AppointmentService;

@SessionAttributes(value = {"user"})
@Controller
public class DoctorController {
	@Autowired
	private AppointmentService appointmentService;
	
	@RequestMapping("/doctordashboard")
	public ModelAndView patientDashboard(HttpSession session,Model model) {
		ModelAndView m = new ModelAndView("doctordashboard");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		Doctor d = (Doctor)session.getAttribute("user");
		List<Appointment> ap_list = this.appointmentService.getAppointmentByDId(d.getDoc_id());
		model.addAttribute("allAppointments", ap_list);
		return m;
	}
	@RequestMapping("/doctorprofile")
	public ModelAndView patientProfile(HttpSession session) {
		ModelAndView m = new ModelAndView("doctorprofile");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return m;
	}
	
}
