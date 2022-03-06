package com.nrifintech.mms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nrifintech.mms.entity.Admin;
import com.nrifintech.mms.entity.Appointment;
import com.nrifintech.mms.entity.Doctor;
import com.nrifintech.mms.service.AdminService;
import com.nrifintech.mms.service.AppointmentService;
import com.nrifintech.mms.service.DoctorService;
import com.nrifintech.mms.util.DoctorExcelExporter;

@SessionAttributes(value = { "user" })
@Controller
public class AdminController {

	@Autowired
	private Admin admin;

	@Autowired
	private AdminService adminService;

	@Autowired
	private Doctor doctor;
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AppointmentService appointmentService;

	@RequestMapping("/admindashboard")
	public ModelAndView patientDashboard(HttpSession session, Model model) {
		ModelAndView m = new ModelAndView("admindashboard");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
		model.addAttribute("allDoctors", doctorList);
		return m;
	}

	@RequestMapping("/addadmin")
	public ModelAndView addadmin(HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return new ModelAndView("addadmin");
	}

	@RequestMapping("/adddoctor")
	public ModelAndView adddoctor(HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		return new ModelAndView("adddoctor");
	}

	@PostMapping("/addnewadmin")
	public ModelAndView addnewAdmin(@RequestParam("email") String username,
			@RequestParam("admin_password") String adminPassword, @RequestParam("password") String password,
			ModelMap map, HttpSession session) {
		ModelAndView m = new ModelAndView("addadmin");
		Admin a = (Admin) session.getAttribute("user");
		System.out.println(a);
		if (a.getPassword().equals(adminPassword)) {
			admin.setUserName(username);
			admin.setPassword(password);
			boolean check = this.adminService.addAdmin(admin);
			if (check) {
				map.put("success", "true");
			} else {
				map.put("error", "true");
			}
		} else {
			map.put("error", "true");
		}
		return m;
	}

	@PostMapping("/addnewdoctor")
	public ModelAndView addnewDoctor(@RequestParam("user_id") Integer id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("specialisation") String specialisation,
			@RequestParam("password") String password, @RequestParam("fees") String fees, ModelMap map,
			HttpSession session) {
		ModelAndView m = new ModelAndView("adddoctor");
		if (session.getAttribute("user") == null)
			return new ModelAndView("index");
		doctor.setAddedBy(Integer.toString(id));
		doctor.setEmail(email);
		doctor.setFees(fees);
		doctor.setName(name);
		doctor.setPassword(password);
		doctor.setSpecialisation(specialisation);
		doctor.setStatus("Active");
		if (this.adminService.addDoctor(doctor) == true) {
			map.put("success", "true");
		} else {
			map.put("error", "true");
		}
		return m;
	}

	@RequestMapping("/enableDoctor")
	public String enableDoctor(@RequestParam("doc_id") Integer doc_id, Model model, ModelMap map) {
		boolean check = this.adminService.checkAndEnable(doc_id);
		if (check) {
			List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
			model.addAttribute("allDoctors", doctorList);
			map.put("success1", "true");
			return "admindashboard";
		}
		List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
		model.addAttribute("allDoctors", doctorList);
		map.put("error1", "true");
		return "admindashboard";
	}

	@RequestMapping("/disableDoctor")
	public String disableDoctor(@RequestParam("doc_id") Integer doc_id, Model model, ModelMap map) {
		boolean check = this.adminService.checkAndDisable(doc_id);
		if (check) {
			List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
			model.addAttribute("allDoctors", doctorList);
			map.put("success", "true");
			return "admindashboard";
		}
		List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
		model.addAttribute("allDoctors", doctorList);
		map.put("error", "true");
		return "admindashboard";
	}

	@RequestMapping("/editdoctor")
	public ModelAndView editDoctor(Model model) {
		ModelAndView m = new ModelAndView("editdoctor");
		List<Doctor> doctorList = this.doctorService.getDoctorListForAdmin();
		model.addAttribute("allDoctors", doctorList);
		return m;
	}

	@RequestMapping("/editform")
	public ModelAndView editForm(@RequestParam("doc_id") Integer doc_id, Model model) {
		ModelAndView m = new ModelAndView();
		Doctor d = this.doctorService.getDoctorById(doc_id);
		model.addAttribute("doctor", d);
		return m;
	}

	@PostMapping("/editdoctorfees")
	public ModelAndView editDetails(@RequestParam("name") String name, @RequestParam("fees") String fees,
			@RequestParam("doc_id") Integer doc_id, Model model) {

		ModelAndView m = new ModelAndView("editform");
		doctor = this.doctorService.getDoctorById(doc_id);
		this.adminService.updateFees(doctor, fees);
		model.addAttribute("success", "Updated fees");
		return m;
	}

	@RequestMapping("/report")
	public ModelAndView report(Model model) {
		ModelAndView m = new ModelAndView("report");
		List<Doctor> allDoctors;
		try {
			allDoctors = this.doctorService.getDoctorListForAdmin();
			for (Doctor d : allDoctors) {
				if (!d.getName().substring(0, 2).equalsIgnoreCase("dr")) {
					d.setName("Dr. " + d.getName());
				}
			}
		} catch (Exception e) {
			allDoctors = null;
		}

		model.addAttribute("allDoctors", allDoctors);
		return m;
	}

	@RequestMapping(path = "/exportdoctors", method = RequestMethod.POST)
	public void exportDoctors(@RequestParam("doc_id") Integer doc_id, @RequestParam("type") String type,
			HttpServletResponse response) {

		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=doctor_" + doc_id + "_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Appointment> appointments;
		DoctorExcelExporter excelExporter;

		try {
			if (type.equals("Weekly")) {
				appointments = this.appointmentService.getWeeklyAppointmentsByDocID(doc_id);
				excelExporter = new DoctorExcelExporter(appointments, false);
			} else if (type.equals("Monthly")) {
				appointments = this.appointmentService.getMonthlyAppointmentsByDocID(doc_id);
				excelExporter = new DoctorExcelExporter(appointments, true);
			} else {
				excelExporter = null;
			}
			excelExporter.export(response);
		} catch (Exception e) {
			System.out.println("Error exporting excel");
		}

	}

}
