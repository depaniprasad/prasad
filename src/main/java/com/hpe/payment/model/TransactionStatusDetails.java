/**
 * 
 */
package com.hpe.payment.model;

import java.util.Date;

/**
 * The Class TransactionStatusDetails.
 *
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
public class TransactionStatusDetails {
	
	/** The transaction log id. */
	private Long transaction_log_id;
	
	/**
	 * Gets the transaction log id.
	 *
	 * @return the transaction log id
	 */
	public Long getTransaction_log_id() {
		return transaction_log_id;
	}
	
	/**
	 * Sets the transaction log id.
	 *
	 * @param transaction_log_id the new transaction log id
	 */
	public void setTransaction_log_id(Long transaction_log_id) {
		this.transaction_log_id = transaction_log_id;
	}
	
	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public Long getTransaction_id() {
		return transaction_id;
	}
	
	/**
	 * Sets the transaction id.
	 *
	 * @param transaction_id the new transaction id
	 */
	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
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
	 * Gets the interim transaction status.
	 *
	 * @return the interim transaction status
	 */
	public String getInterim_transaction_status() {
		return interim_transaction_status;
	}
	
	/**
	 * Sets the interim transaction status.
	 *
	 * @param interim_transaction_status the new interim transaction status
	 */
	public void setInterim_transaction_status(String interim_transaction_status) {
		this.interim_transaction_status = interim_transaction_status;
	}
	
	/**
	 * Gets the pg status code.
	 *
	 * @return the pg status code
	 */
	public String getPg_status_code() {
		return pg_status_code;
	}
	
	/**
	 * Sets the pg status code.
	 *
	 * @param pg_status_code the new pg status code
	 */
	public void setPg_status_code(String pg_status_code) {
		this.pg_status_code = pg_status_code;
	}
	
	/**
	 * Gets the pg status message.
	 *
	 * @return the pg status message
	 */
	public String getPg_status_message() {
		return pg_status_message;
	}
	
	/**
	 * Sets the pg status message.
	 *
	 * @param pg_status_message the new pg status message
	 */
	public void setPg_status_message(String pg_status_message) {
		this.pg_status_message = pg_status_message;
	}
	
	/**
	 * Gets the pg error code.
	 *
	 * @return the pg error code
	 */
	public String getPg_error_code() {
		return pg_error_code;
	}
	
	/**
	 * Sets the pg error code.
	 *
	 * @param pg_error_code the new pg error code
	 */
	public void setPg_error_code(String pg_error_code) {
		this.pg_error_code = pg_error_code;
	}
	
	/**
	 * Gets the pg error message.
	 *
	 * @return the pg error message
	 */
	public String getPg_error_message() {
		return pg_error_message;
	}
	
	/**
	 * Sets the pg error message.
	 *
	 * @param pg_error_message the new pg error message
	 */
	public void setPg_error_message(String pg_error_message) {
		this.pg_error_message = pg_error_message;
	}
	
	/**
	 * Gets the request response.
	 *
	 * @return the request response
	 */
	public String getRequest_response() {
		return request_response;
	}
	
	/**
	 * Sets the request response.
	 *
	 * @param request_response the new request response
	 */
	public void setRequest_response(String request_response) {
		this.request_response = request_response;
	}
	
	/**
	 * Gets the pg transaction id.
	 *
	 * @return the pg transaction id
	 */
	public String getPg_transaction_id() {
		return pg_transaction_id;
	}
	
	/**
	 * Sets the pg transaction id.
	 *
	 * @param pg_transaction_id the new pg transaction id
	 */
	public void setPg_transaction_id(String pg_transaction_id) {
		this.pg_transaction_id = pg_transaction_id;
	}
	
	/**
	 * Gets the last updated time.
	 *
	 * @return the last updated time
	 */
	public Date getLast_updated_time() {
		return last_updated_time;
	}
	
	/**
	 * Sets the last updated time.
	 *
	 * @param last_updated_time the new last updated time
	 */
	public void setLast_updated_time(Date last_updated_time) {
		this.last_updated_time = last_updated_time;
	}
	
	/** The transaction id. */
	private Long transaction_id;
	
	/** The createddatetime. */
	private Date createddatetime;
	
	/** The interim transaction status. */
	private String interim_transaction_status;
	
	/** The pg status code. */
	private String pg_status_code;
	
	/** The pg status message. */
	private String pg_status_message;
	
	/** The pg error code. */
	private String pg_error_code;
	
	/** The pg error message. */
	private String pg_error_message;
	
	/** The request response. */
	private String request_response;
	
	/** The pg transaction id. */
	private String pg_transaction_id;
	
	/** The last updated time. */
	private Date last_updated_time;
}