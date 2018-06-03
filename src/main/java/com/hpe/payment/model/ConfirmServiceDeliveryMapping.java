package com.hpe.payment.model;

import java.io.Serializable;

/**
 * The Class ConfirmServiceDeliveryMapping.
 * 
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
public class ConfirmServiceDeliveryMapping implements Serializable  {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The confirm service delivery response. */
	private ConfirmServiceDeliveryResponse confirmServiceDeliveryResponse;

	/**
	 * Gets the confirm service delivery response.
	 *
	 * @return the confirm service delivery response
	 */
	public ConfirmServiceDeliveryResponse getConfirmServiceDeliveryResponse() {
		return confirmServiceDeliveryResponse;
	}

	/**
	 * Sets the confirm service delivery response.
	 *
	 * @param confirmServiceDeliveryResponse the new confirm service delivery response
	 */
	public void setConfirmServiceDeliveryResponse(ConfirmServiceDeliveryResponse confirmServiceDeliveryResponse) {
		this.confirmServiceDeliveryResponse = confirmServiceDeliveryResponse;
	}

}