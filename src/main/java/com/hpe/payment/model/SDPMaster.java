package com.hpe.payment.model;

import java.util.Date;

/**
 * The Class SDPMaster.
 * 
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
public class SDPMaster {

	/** The transaction id. */
	private long transaction_id;
	
	/** The fullname. */
	private String fullname;
	
	/** The email. */
	private String email;
	
	/** The amout. */
	private float amout;
	
	/** The transaction status. */
	private String transaction_status;
	
	/** The details. */
	private String details;
	
	/** The createddatetime. */
	private Date createddatetime;
	
	/** The lastupdateddatatetime. */
	private Date lastupdateddatatetime;
	
	/** The confirm service. */
	private String confirm_service;
	
	/** The telephone. */
	private String telephone;

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public long getTransaction_id() {
		return transaction_id;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transaction_id the new transaction id
	 */
	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * Gets the fullname.
	 *
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Sets the fullname.
	 *
	 * @param fullname the new fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the amout.
	 *
	 * @return the amout
	 */
	public float getAmout() {
		return amout;
	}

	/**
	 * Sets the amout.
	 *
	 * @param amout the new amout
	 */
	public void setAmout(float amout) {
		this.amout = amout;
	}

	/**
	 * Gets the transaction status.
	 *
	 * @return the transaction status
	 */
	public String getTransaction_status() {
		return transaction_status;
	}

	/**
	 * Sets the transaction status.
	 *
	 * @param transaction_status the new transaction status
	 */
	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Gets the createddatetime.
	 *
	 * @return the createddatetime
	 */
	public Date getCreateddatetime() {
		return createddatetime;
	}

	/**
	 * Sets the createddatetime.
	 *
	 * @param createddatetime the new createddatetime
	 */
	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	/**
	 * Gets the lastupdateddatatetime.
	 *
	 * @return the lastupdateddatatetime
	 */
	public Date getLastupdateddatatetime() {
		return lastupdateddatatetime;
	}

	/**
	 * Sets the lastupdateddatatetime.
	 *
	 * @param lastupdateddatatetime the new lastupdateddatatetime
	 */
	public void setLastupdateddatatetime(Date lastupdateddatatetime) {
		this.lastupdateddatatetime = lastupdateddatatetime;
	}

	/**
	 * Gets the confirm service.
	 *
	 * @return the confirm service
	 */
	public String getConfirm_service() {
		return confirm_service;
	}

	/**
	 * Sets the confirm service.
	 *
	 * @param confirm_service the new confirm service
	 */
	public void setConfirm_service(String confirm_service) {
		this.confirm_service = confirm_service;
	}

	/**
	 * Gets the telephone.
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Sets the telephone.
	 *
	 * @param telephone the new telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/*
	 * public String toString(){
	 * 
	 * return super.toString(); }
	 */
}