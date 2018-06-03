package com.hpe.payment.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hpe.payment.model.SDPMaster;
import com.hpe.payment.model.TransactionStatusDetails;

/**
 * SDPMaster - ImplementationClass- for connecting to Database   .
 *
 * @author : 16-August-2017 - DXC Technology - Created by Urukunda Reddy
 * 
 */
@Repository
//@Qualifier("sdpservicesDao")

public class SDPServicesDaoImpl implements SDPServicesDao {

	/** The jdbc template. */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/** The env. */
	@Autowired
	Environment env;
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(SDPServicesDaoImpl.class);
	
	
	/**
	 * This method Fetches records Transaction Status ="Pending" or 
	 * Confirm_service ="CONFIRM_SERVICE_FAILURE" .
	 *
	 * @return List<SDPMaster>
	 */
	public List<SDPMaster> getPendingStatusRecords() {
		log.info("Inside class: SDPServicesDaoImpl ::  method: getPendingStatusRecords -> Start");
		/*String sql = "select * From sdp_transaction_master where transaction_status ='" + env.getProperty("STATUS_PENDING")
				+ "' or confirm_service ='" + env.getProperty("CONFIRM_SERVICE_FAILURE") + "'";*/
		
		String sql = "SELECT * FROM sdp_transaction_master WHERE transaction_status ='" + env.getProperty("STATUS_PENDING")
				+ "' OR confirm_service ='" + env.getProperty("CONFIRM_SERVICE_FAILURE") 
				+ "' AND transaction_status NOT IN ('" + env.getProperty("CONFIRM_SERVICE_CANCELLED") + "') AND createddatetime < (CURRENT_TIMESTAMP - interval '5 minutes' )";
		log.info("Pending Stauts Records Query ---->;" + sql);
		try {
			List<SDPMaster> sdpmaster = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SDPMaster>(SDPMaster.class));
			if (sdpmaster.size() > 0) {
				return sdpmaster;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Exception in getting the Pending Status Records from DB "+ e.getMessage());
			e.printStackTrace();
		}
		log.info("Inside class: SDPServicesDaoImpl ::  method: getPendingStatusRecords -> Ends");
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#insertTransactionStatus(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	/**
	 * This method inserts records into sdp_transaction_log table
	 * @param response,transactionId, messageCode,  messageText, degTrn
	 * @return void
	 * * @throws Exception 
	 */
	public void insertTransactionStatus(String response, Long transactionId, String messageCode, String messageText,String degTrn, String interim_transaction_status) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertTransactionStatus -> Start");
		String methodName = "insertTransactionStatus";
		String sql = "Insert into sdp_transaction_log(request_response, pg_status_code,pg_status_message,interim_transaction_status,last_updated_time,pg_transaction_id,transaction_id) VALUES(?,?,?,?,?,?,?)";
		log.info("InsertTransactionStatus Query ---->" + sql);
		Object[] params = { response, messageCode, messageText, interim_transaction_status, new Timestamp(new Date().getTime()), degTrn, transactionId };
		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR,Types.BIGINT };
		dataBaseTransaction(sql, params, types, methodName);
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertTransactionStatus -> Ends");
	}

	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#updateTransMaster(java.lang.String, java.lang.Long)
	 */
	@Override
	/**
	 * This method Updates records in sdp_transaction_master table
	 * @param String message, Long transactionId, String messageCode
	 * @return void
	 * * @throws Exception 
	 */
	public void updateTransMaster(String message, Long transactionId, String messageCode, String degTrn) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: updateTransMaster -> Starts");
		String methodName = "updateTransMaster";
		String sql = "update sdp_transaction_master set transaction_status =?,last_updated_time =?, transcode=?, degTrn=? where transaction_id=?";
		log.info("updateTransMaster Query ---->"+sql);
		Object[] params = { message, new Timestamp(new Date().getTime()), messageCode, degTrn, transactionId };
		int[] types = { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
		dataBaseTransaction(sql, params, types, methodName);
		log.info("Inside class: SDPServicesDaoImpl ::  method: updateTransMaster -> Ends");
	}

	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#insertConfirmServReq(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	/**
	 * This method inserts records in sdp_transaction_log table
	 * @param String response, Long transactionid, String messageCode, String messageText
	 * @return void
	 * * @throws Exception 
	 */
	public void insertConfirmServReq(String response, Long transactionid, String messageCode, String messageText) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertConfirmServReq -> Starts");
		String methodName = "insertConfirmServReq";
		String interim_transaction_status = env.getProperty("CONFIRM_SERVICE_DELIVERY_REQUEST");
		String sql = "INSERT INTO sdp_transaction_log(request_response, pg_status_code,pg_status_message,interim_transaction_status,last_updated_time,transaction_id) VALUES(?,?,?,?,?,?)";
		log.info("InsertConfirmServReq Query ---->" + sql);
		Object[] params = { response, messageCode, messageText, interim_transaction_status, new Timestamp(new Date().getTime()), transactionid };
		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT };
		dataBaseTransaction(sql, params, types, methodName );
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertConfirmServReq -> Ends");
	}

	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#insertConfirmDeliveryStatus(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	/**
	 * This method inserts records in sdp_transaction_log table
	 * @param String response, String interium_status, Long transactionid
	 * @return void
	 * * @throws Exception 
	 */
	public void insertConfirmDeliveryStatus(String response, String interium_status, Long transactionid) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertConfirmDeliveryStatus -> Starts");	
		String methodName = "insertConfirmDeliveryStatus";
		String interim_transaction_status = interium_status;
		String sql = "INSERT INTO sdp_transaction_log(request_response,interim_transaction_status,last_updated_time,transaction_id) VALUES(?,?,?,?)";
		log.info("InsertConfirmDeliveryStatus Query " + sql);
		Object[] params = { response, interim_transaction_status, new Timestamp(new Date().getTime()), transactionid };
		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT };
		dataBaseTransaction(sql, params, types, methodName);
		log.info("Inside class: SDPServicesDaoImpl ::  method: insertConfirmDeliveryStatus -> Ends");

	}

	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#updateConfirmStatusMaster(java.lang.String, java.lang.Long)
	 */
	@Override
	/**
	 * This method updates records in sdp_transaction_master table
	 * @param String interium_status, Long transaction_id, String messageCode
	 * @return void
	 * * @throws Exception 
	 */
	public void updateConfirmStatusMaster(String interium_status, Long transactionid, String messageCode) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: updateConfirmStatusMaster -> Starts");
		String methodName = "updateConfirmStatusMaster";
		String sql = "update sdp_transaction_master set confirm_service =?,last_updated_time=?, transcode=? where transaction_id =? ";
		log.info("updateConfirmStatusMaster Query " + sql);
		Object[] params = { interium_status, new Timestamp(new Date().getTime()), messageCode, transactionid };
		int[] types = { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.BIGINT };
		dataBaseTransaction(sql, params, types, methodName);
		log.info("Inside class: SDPServicesDaoImpl ::  method: updateConfirmStatusMaster -> Ends");
	}


	/* (non-Javadoc)
	 * @see com.hpe.payment.dao.SDPServicesDao#getTransactionStatusDetails(java.lang.Long)
	 */
	@Override
	public List<TransactionStatusDetails> getTransactionStatusDetails(
			Long transactionId) {
		log.info("Inside class: SDPServicesDaoImpl ::  method: updateConfirmStatusMaster -> Starts");
		
		//SELECT pg_status_code, pg_status_message, pg_transaction_id from sdp_transaction_log where transaction_id = 306 and interim_transaction_status = 'PAYMENT_SUCCESS' ;
		String sql = "select * From sdp_transaction_log where transaction_id = '"+transactionId +"' and interim_transaction_status ='" + env.getProperty("PAYMENT_SUCCESS")+ "'";
		log.debug("Pending Stauts Records Query ---->;" + sql);
		try {
			List<TransactionStatusDetails> transactionStatusDetails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<TransactionStatusDetails>(TransactionStatusDetails.class));
			if (null != transactionStatusDetails && !"".equals(transactionStatusDetails)) {
				log.info("transactionStatusDetails.size()----> "+ transactionStatusDetails.size());
				//log.info("transactionStatusDetails.getPg_transaction_id----> "+ transactionStatusDetails.get(0).getPg_transaction_id());
			}
			if (transactionStatusDetails.size() > 0) {
				return transactionStatusDetails;
			} else {
				return null;
			}

		} catch (Exception e) {
			log.error("Exception in getting the Pending Status Records from DB "+ e.getMessage());
			e.printStackTrace();
		}
		log.info("Inside class: SDPServicesDaoImpl ::  method: getPendingStatusRecords -> Ends");
		return null;
	}

	/**
	 * This method inserts OR updates records in sdp_transaction_master OR sdp_transaction_log table.
	 *
	 * @param sql - Sql Query to insert or update the sdp_transaction_master OR sdp_transaction_log table
	 * @param params - Parameters for the database transaction
	 * @param types - data types of the parameters
	 * @param methodName - From which method invoking this method
	 */
	private void dataBaseTransaction(String sql, Object[] params, int[] types, String methodName) {
		try {
			log.info("Inserting the " +methodName+ " into Data Base");
			int rows = jdbcTemplate.update(sql, params, types);
			log.info(rows + " row(s) inserted.");
		} catch (Exception e) {
			log.error("Error in inserting the " +methodName+ "->" + e.getMessage());
		}
	}
}