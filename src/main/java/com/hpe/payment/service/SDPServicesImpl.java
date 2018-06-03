package com.hpe.payment.service;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.payment.dao.SDPServicesDao;
import com.hpe.payment.model.ConfirmServiceDeliveryMapping;
import com.hpe.payment.model.SDPMaster;
import com.hpe.payment.model.TransactionStatusDetails;




import org.apache.log4j.Logger;

/**
 * SDPMaster - ImplementationClass- for Services in SDPServices Interface   .
 *
 * @author : 16-August-2017 - DXC Technology - Created by Urukunda Reddy
 */
@Service("sdpservices")
public class SDPServicesImpl implements SDPServices{

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(SDPServicesImpl.class);
	
	/** The sdpservices dao. */
	@Autowired
	SDPServicesDao sdpservicesDao ;	
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#getPendingStatusRecords()
	 */
	public List < SDPMaster > getPendingStatusRecords() {
        return sdpservicesDao.getPendingStatusRecords();
    }	
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#insertTransactionStatus(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertTransactionStatus(String response, Long transactionId,String messageCode, String messageText,String degTrn, String interim_transaction_status) {		
		sdpservicesDao.insertTransactionStatus(response,transactionId,messageCode,messageText,degTrn, interim_transaction_status);		
	}
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#insertConfirmDeliveryStatus(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public void insertConfirmDeliveryStatus(String response, String interium_status,Long transactionid) {		
		sdpservicesDao.insertConfirmDeliveryStatus(response, interium_status,transactionid);
	}
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#insertConfirmServReq(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertConfirmServReq(String response, Long transactionid, String messageCode, String messageText) {		
		sdpservicesDao.insertConfirmServReq(response, transactionid, messageCode, messageText);		
	}
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#updateTransMaster(java.lang.String, java.lang.Long)
	 */
	@Override
	public void updateTransMaster(String message, Long transactionId, String messageCode, String degTrn) {		
		sdpservicesDao.updateTransMaster(message,transactionId, messageCode, degTrn);	
	}
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#updateConfirmStatusMaster(java.lang.String, java.lang.Long)
	 */
	@Override
	public void updateConfirmStatusMaster(String message, Long transactionId, String messageCode) {		
		sdpservicesDao.updateConfirmStatusMaster(message,transactionId, messageCode);		
	}
	
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#getConfirmationStatus(java.lang.String)
	 */
	@Override
	/**
	 * This method parses the Confirmation Service Response and returns boolean flag
	 * @return Boolean
	 * @throws JsonParseException 
	 */
	public boolean getConfirmationStatus(String response) {
		log.info("Inside class: SDPServicesImpl ::  method: getConfirmationStatus -> Start");
		ConfirmServiceDeliveryMapping ps = null;
		boolean flag = false;
		ObjectMapper mapper = new ObjectMapper();
		log.info("Inside class: SDPServicesImpl ::  response -> " +response);
		//response -> {"confirmServiceDeliveryResponse":{"errorSpecified":true,"valid":false,"error":{"messageSpecified":true,"code":"TXN0010","message":"Transaction not found"}}}
		try {
			ps = mapper.readValue(response, ConfirmServiceDeliveryMapping.class);
			log.debug("Valid Value :::"+ ps.getConfirmServiceDeliveryResponse().getValid());
			flag = Boolean.parseBoolean((ps.getConfirmServiceDeliveryResponse().getValid()));
		} catch (JsonParseException e) {
			log.error("JsonParseException ---->" + e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("JsonMappingException ---->" +e.getMessage() );
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException ----> " +e.getMessage());
			e.printStackTrace();
		}
		log.info("flag ======>>>"+flag);
		log.info("Inside class: SDPServicesImpl ::  method: getConfirmationStatus -> Ends");
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hpe.payment.service.SDPServices#getTransactionStatusDetails(java.lang.Long)
	 */
	@Override
	public List<TransactionStatusDetails> getTransactionStatusDetails(
			Long transactionId) {
		return sdpservicesDao.getTransactionStatusDetails(transactionId);
	}		
}