package com.hpe.payment.dao;

import java.util.List;

import com.hpe.payment.model.SDPMaster;
import com.hpe.payment.model.TransactionStatusDetails;

/**
 * SDPMaster - Iterface- Methods for connecting to Database   .
 *
 * @author : 16-August-2017 - DXC Technology - Created by Urukunda Reddy
 * 
 */

public interface SDPServicesDao {	
	
	/**
	 * Gets the pending status records.
	 *
	 * @return the pending status records
	 */
	public List<SDPMaster> getPendingStatusRecords();
	
	/**
	 * Insert transaction status.
	 *
	 * @param response the response
	 * @param transactionId the transaction id
	 * @param messageCode the message code
	 * @param messageText the message text
	 * @param degTrn the deg trn
	 * @param interim_transaction_status the interim transaction status
	 */
	public void insertTransactionStatus(String response, Long transactionId,String messageCode, String messageText,String degTrn, String interim_transaction_status);
	
	/**
	 * Update trans master.
	 *
	 * @param message the message
	 * @param transactionId the transaction id
	 */
	public void updateTransMaster(String message, Long transactionId, String messageCode, String degTrn);
	
	/**
	 * Insert confirm serv req.
	 *
	 * @param response the response
	 * @param transactionid the transactionid
	 * @param messageCode the message code
	 * @param messageText the message text
	 */
	public void insertConfirmServReq(String response, Long transactionid, String messageCode, String messageText) ;
	
	/**
	 * Insert confirm delivery status.
	 *
	 * @param response the response
	 * @param status the status
	 * @param transactionid the transactionid
	 */
	public void insertConfirmDeliveryStatus(String response,String status,Long transactionid);
	
	/**
	 * Update confirm status master.
	 *
	 * @param message the message
	 * @param transactionid the transactionid
	 * @param messageCode the messageCode
	 */
	public void updateConfirmStatusMaster(String message,Long transactionid, String messageCode);	
	
	/**
	 * Gets the transaction status details.
	 *
	 * @param transactionId the transaction id
	 * @return the transaction status details
	 */
	public List<TransactionStatusDetails> getTransactionStatusDetails(Long transactionId);
}
