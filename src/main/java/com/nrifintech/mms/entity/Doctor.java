package com.nrifintech.mms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doc_id;
	private String name;
	private String email;
	private String status;
	private String specialisation;
	private String fees;
	private String password;
	private String addedBy;
	
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Doctor(int doc_id, String name, String email, String status, String specialisation, String fees,
			String password, String addedBy) {
		super();
		this.doc_id = doc_id;
		this.name = name;
		this.email = email;
		this.status = status;
		this.specialisation = specialisation;
		this.fees = fees;
		this.password = password;
		this.addedBy = addedBy;
	}

	public int getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	@Override
	public String toString() {
		return "Doctor [doc_id=" + doc_id + ", name=" + name + ", email=" + email + ", status=" + status
				+ ", specialisation=" + specialisation + ", fees=" + fees + ", password=" + password + ", addedBy="
				+ addedBy + "]";
	}

	
}
