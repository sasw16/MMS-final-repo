package com.nrifintech.mms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.nrifintech.mms.entity.Doctor;

@Repository
public class DoctorDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private List<Doctor> list;
	public List<Doctor> fetchDoctorList() {
		list = this.hibernateTemplate.loadAll(Doctor.class);
		return list;
	}
	
	public Doctor getDoctorById(Integer id) {
		return this.hibernateTemplate.get(Doctor.class, id);
	}
}
