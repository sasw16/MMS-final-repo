package com.nrifintech.mms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrifintech.mms.dao.AdminDao;
import com.nrifintech.mms.dao.AppointmentDao;
import com.nrifintech.mms.entity.Admin;
import com.nrifintech.mms.entity.Appointment;
import com.nrifintech.mms.entity.Doctor;



@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	private List<Admin> list;
	@Autowired
	private Admin admin;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private AppointmentDao appointmentDao;
	
	public boolean verifyAdmin(Admin admin) {
		list = this.adminDao.getAll();
		for(Admin ad:list) {
			if(ad.getA_id() == admin.getA_id() && ad.getPassword().equals(admin.getPassword())) {
				//patient = this.patientDaoImpl.getPatientById(pr.getPid());
				
				
				return true;
			}
		}
		return false;
	}
	
	public Admin getAdmin(Admin a) {
		list = this.adminDao.getAll();
		for(Admin ad:list) {
			if(ad.getA_id() == a.getA_id() && ad.getPassword().equals(a.getPassword())) {
				admin = this.adminDao.getAdminById(ad.getA_id());
			}
		}
		return admin;
	}
	public Admin getAdminById(Integer id) {
		return this.adminDao.getAdminById(id);
	}
	
	public boolean addAdmin(Admin a) {
		list = this.adminDao.getAll();
		for(Admin ad:list) {
			if(ad.getUserName().equals(a.getUserName()))
				return false;
		}
		
		this.adminDao.register(a);
		return true;
		
	}
	
	public boolean addDoctor(Doctor d) {
		List<Doctor> allDoctors = this.adminDao.getAllDoctors();
		for(Doctor doc:allDoctors) {
			if(doc.getEmail().equals(d.getEmail()))
				return false;
		}
		
		this.adminDao.addNewDoctor(d);
		return true;
		
	}
	
	public boolean checkAndDisable(Integer id) {
		Doctor doctor = this.doctorService.getDoctorById(id);
		
		List<Appointment> fetched = this.appointmentDao.fetchAppointmentList();
		for(Appointment a:fetched){
			if(a.getDoc_id() == id && a.getStatus().equals("Active"))
				return false;
		}
		this.adminDao.checkAndDisable(doctor);
		return true;
	}
	
	public boolean checkAndEnable(Integer id) {
		Doctor doctor = this.doctorService.getDoctorById(id);
		if(doctor.getStatus().equals("Inactive")) {
			this.adminDao.checkAndEnable(doctor);
			return true;
		}
		else return false;
	}
	
	public void updateFees(Doctor d,String fees) {
		this.adminDao.updateFees(d,fees);
	}
	
}
