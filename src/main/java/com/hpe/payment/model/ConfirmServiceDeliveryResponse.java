package com.hpe.payment.model;

/**
 * The Class ConfirmServiceDeliveryResponse.
 *
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
public class ConfirmServiceDeliveryResponse {
	
	/** The error. */
	private ConfirmServiceDeliveryErrorResponse error ;
	
	/** The valid. */
	private String valid;
	
	/** The error specified. */
	private String errorSpecified;
	
	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public ConfirmServiceDeliveryErrorResponse getError() {
		return error;
	}
	
	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(ConfirmServiceDeliveryErrorResponse error) {
		this.error = error;
	}
	
	/**
	 * Gets the valid.
	 *
	 * @return the valid
	 */
	public String getValid() {
		return valid;
	}
	
	/**
	 * Sets the valid.
	 *
	 * @param valid the new valid
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	/**
	 * Gets the error specified.
	 *
	 * @return the error specified
	 */
	public String getErrorSpecified() {
		return errorSpecified;
	}
	
	/**
	 * Sets the error specified.
	 *
	 * @param errorSpecified the new error specified
	 */
	public void setErrorSpecified(String errorSpecified) {
		this.errorSpecified = errorSpecified;
	}
}
