package com.nrifintech.mms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrifintech.mms.dao.DoctorDao;

import com.nrifintech.mms.entity.Doctor;

@Service
public class DoctorService {

	@Autowired
	private DoctorDao doctorDao;
	@Autowired
	private Doctor doctor;
	
	private List<Doctor> list;
	public boolean verifyDoctor(Doctor d) {
		list = this.doctorDao.fetchDoctorList();
		for(Doctor doc:list) {
			if(doc.getEmail().equalsIgnoreCase(d.getEmail()) && doc.getPassword().equals(d.getPassword())) {
				return true;
			}
		}
		
		return false;
	}
	
	public Doctor getDoctor(Doctor d) {
		list = this.doctorDao.fetchDoctorList();
		for(Doctor doc:list) {
			if(doc.getEmail().equalsIgnoreCase(d.getEmail()) && doc.getPassword().equals(d.getPassword())) {
				doctor = this.doctorDao.getDoctorById(doc.getDoc_id());
			}
		}
		return doctor;
	}
	
	
	public List<Doctor> filterDoctorForPatient(String spcl){
		list = this.doctorDao.fetchDoctorList();
		List<Doctor> filtered = new ArrayList<>();
		for(Doctor doc: list) {
			if(doc.getSpecialisation().equals(spcl) && doc.getStatus().equals("Active"))
				filtered.add(doc);
		}
		
		return filtered;
	}
	
	public Doctor getDoctorById(Integer id) {
		return this.doctorDao.getDoctorById(id);
	}
	
	
	
	public List<Doctor> getDoctorListForAdmin(){
		
		list = this.doctorDao.fetchDoctorList();
		
		return list;
	}
	
	
	
	
	public List<Doctor> getDoctorListForPatients(){
		List<Doctor> doctors= new ArrayList<>();
		list = this.doctorDao.fetchDoctorList();
		for(Doctor d:list)
			if(d.getStatus().equals("Active"))
				doctors.add(d);
		return doctors;
	}
}
