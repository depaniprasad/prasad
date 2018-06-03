package com.hpe.payment.model;

/**
 * The Class ConfirmServiceDeliveryErrorResponse.
 * 
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
public class ConfirmServiceDeliveryErrorResponse {
	
	/** The code. */
	public String code;
	
	/** The message specified. */
	public String messageSpecified;
	
	/** The message. */
	public String message;
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the message specified.
	 *
	 * @return the message specified
	 */
	public String getMessageSpecified() {
		return messageSpecified;
	}
	
	/**
	 * Sets the message specified.
	 *
	 * @param messageSpecified the new message specified
	 */
	public void setMessageSpecified(String messageSpecified) {
		this.messageSpecified = messageSpecified;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}