package com.hpe.payment.scheduler;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.hpe.payment.configuration.ApplicationConfig;
import com.hpe.payment.configuration.Constants;
import com.hpe.payment.service.SDPServices;
import com.hpe.payment.model.SDPMaster;
import com.hpe.payment.model.TransactionStatusDetails;
import com.hpe.payment.rest.RestClient;

import org.apache.log4j.Logger;

/**
 * The Class PaymentScheduler.
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
@Configuration
@EnableScheduling
@Component
@PropertySource("classpath:application.properties")
/**
 * @author : 16-August-2017 - DXC Technology - Created by Urukunda Reddy
 */
public class PaymentScheduler {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(PaymentScheduler.class);
	
	/** The env. */
	@Autowired
	Environment environment;

	/**
	 * This method Starts scheduler.s
	 *
	 * @return Void
	 */

	@Scheduled(cron = "${scheduling.job.cron}")
	public void startScheduler() {
		log.info("********************TransactionEnquiry Scheduler Status *************************");
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		SDPServices sdpservices = (SDPServices) context.getBean("sdpservices");
		RestClient restClient = new RestClient();
		List<SDPMaster> sdpmaster = null;
		try {
			sdpmaster = sdpservices.getPendingStatusRecords();
		} catch (Exception ep) {
			log.error("Error in getting the TransctionID's from Database" + ep.getMessage());
		}
		ResponseEntity<String> confirmResponse = null;
		try {
			if (sdpmaster != null) {
				log.info("No.of Pending Or CONFIRM_SERVICE_FAILURE Tranasaction/s Fetched From DB ---->" + sdpmaster.size());
				for (SDPMaster master : sdpmaster) {
					log.info("Tranasaction ID ::" + master.getTransaction_id());
					String messageCode = null;
					String messageText = null;
					String degTrn = null;
					if (master.getTransaction_status().equalsIgnoreCase(environment.getProperty(Constants.STATUS_PENDING))) {
						String response = null;
						try {
							response = restClient.getTransactionStatus(master.getTransaction_id());
							log.info("Transaction Response Received --->"+ response);
						} catch (Exception e) {
							log.error("Error in Getting the transaction response "+ e.getMessage());
						}
						if (!response.equals("") || response != null) {
							Document doc = null;
							try {
								doc = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
										.parse(new InputSource(new StringReader(response)));
							} catch (SAXException e) {
								log.error("SAXException ......"+ e.getMessage());
							} catch (IOException e) {
								log.error("IOException ------" + e.getMessage());
							} catch (ParserConfigurationException e) {
								log.error("ParserConfigurationException ....."+ e.getMessage());
							}
							NodeList messageNode = doc.getElementsByTagName(Constants.TRANSACTION);
							log.info("messageNode ----->" + messageNode);
							if (null != messageNode) {
								Element message = (Element) messageNode.item(0);
								log.info("message.getChildNodes().getLength() ----->" + message.getChildNodes().getLength());
								messageCode = message.getElementsByTagName(Constants.MESSAGECODE).item(0).getTextContent();
								messageText = message.getElementsByTagName(Constants.MESSAGE).item(0).getTextContent();
								log.info("messageCode Length ----->" + messageNode.getLength());
								if( message.getChildNodes().getLength() > 2) {
								degTrn = message.getElementsByTagName(Constants.DEGTRN).item(0).getTextContent();
								} else {
									degTrn = "";
								}
								log.info("messageCode ----->" + messageCode);
								log.info("messageText ---->" + messageText);
								log.info("DEGTRN ---->" + degTrn);
								if (Integer.parseInt(messageCode) == 0 && messageText.equals(environment.getProperty(Constants.SUCCESS))) {
									log.info("Inside the if success message");
									boolean flag = false;
									try {
										String interim_transaction_status = environment.getProperty(Constants.PAYMENT_SUCCESS);
										sdpservices.insertTransactionStatus(response,master.getTransaction_id(), messageCode,messageText, degTrn,interim_transaction_status);
										sdpservices.updateTransMaster(interim_transaction_status, master.getTransaction_id(),messageCode,degTrn);
										sdpservices.insertConfirmServReq(response, master.getTransaction_id(), messageCode, messageText);
									} catch (Exception e) {
										log.error("Error in Inserting the Service Confirmation Request" + e.getMessage());
									}
									confirmResponse = getConfirmationDetails(sdpservices, restClient, confirmResponse, master, messageCode, messageText, String.valueOf(master.getTransaction_id()),
											flag);
								} else if ((Integer.parseInt(messageCode) == 80013) || (Integer.parseInt(messageCode) == 80014)) {
									String interim_transaction_status = Constants.PENDING;
									log.info(" Transaction is Pending Capture OR In Progress, Not Updating the master table");
									sdpservices.insertTransactionStatus(response,master.getTransaction_id(), messageCode,messageText, degTrn,interim_transaction_status);
								} else if ((Integer.parseInt(messageCode) == 11) ) {
									log.info(" Transaction is SP TERMINATED ");
									try {
										String interim_transaction_status = Constants.SP_TERMINATED;
										sdpservices.insertTransactionStatus(response,master.getTransaction_id(), messageCode,messageText, degTrn,interim_transaction_status);
										sdpservices.updateTransMaster(Constants.FAILURE, master.getTransaction_id(),messageCode, degTrn);
									
									} catch (Exception e){
										log.error(" Error in insterting the master table : " +e.getMessage());
									}
								} else if ((Integer.parseInt(messageCode) == 10) ) {
									log.info(" Transaction is CANCELLED ");
									try {
										String interim_transaction_status = Constants.CANCELLED;
										sdpservices.insertTransactionStatus(response,master.getTransaction_id(), messageCode,messageText, degTrn,interim_transaction_status);
										sdpservices.updateTransMaster(interim_transaction_status, master.getTransaction_id(),messageCode, degTrn);
									} catch (Exception e){
										log.error(" Error in insterting the master table : " +e.getMessage());
									}
								} else {
									log.info(" Transaction is FAILURE ");
									try {
										String interim_transaction_status = Constants.FAILURE;
										sdpservices.insertTransactionStatus(response,master.getTransaction_id(), messageCode,messageText, degTrn,interim_transaction_status);
									    sdpservices.updateTransMaster(interim_transaction_status, master.getTransaction_id(),messageCode, degTrn);
									} catch (Exception e){
										log.error(" Error in insterting the master table : " +e.getMessage());
									}
								}
							} 
						}
					} else if (master.getConfirm_service().equals(environment.getProperty(Constants.CONFIRM_SERVICE_FAILURE))) {
						boolean flag = false;
						try {
							log.info("Try ........... :: CONFIRM_SERVICE_FAILURE");
							List<TransactionStatusDetails> transactionStatusDetails = null;
							transactionStatusDetails = sdpservices.getTransactionStatusDetails(master.getTransaction_id());
							if (null != transactionStatusDetails) {
							degTrn = transactionStatusDetails.get(0).getPg_transaction_id();
							messageCode = transactionStatusDetails.get(0).getPg_status_code();
							messageText = transactionStatusDetails.get(0).getPg_status_message();
							log.info("degTrn ::" + degTrn);
							log.info("messageCode ::" + messageCode);
							log.info("messageText ::" + messageText);
							confirmResponse = getConfirmationDetails(sdpservices, restClient, confirmResponse, master, messageCode, messageText, degTrn,
									flag);
							}
						} catch (Exception e) {
							log.error("Error in getting the response " + e.getMessage());
						}
					}
				}
			} else {
				log.info("No Records Found in Pending Status or confirm_servicefailure status");
			}
		} catch (Exception e) {
			log.error("Error -----" + e.getMessage());
		} finally {
			context.close();
		}
	}

	/**
	 * Gets the confirmation details.
	 *
	 * @param sdpservices the sdpservices
	 * @param restClient the rest client
	 * @param confirmResponse the confirm response
	 * @param master the master
	 * @param messageCode the message code
	 * @param messageText the message text
	 * @param degTrn the deg trn
	 * @param flag the flag
	 * @return the confirmation details
	 */
	private ResponseEntity<String> getConfirmationDetails(
			SDPServices sdpservices, RestClient restClient,
			ResponseEntity<String> confirmResponse, SDPMaster master,
			String messageCode, String messageText, String degTrn, boolean flag) {
		try {
			confirmResponse = restClient.getConfirmationService(environment.getProperty(Constants.SPCODE), environment.getProperty(Constants.SERVCODE), String.valueOf(degTrn), messageCode, messageText);
			// confirmResponse = restClient.getConfirmationService("ZFND", "test", "1407142212465","0", "Success");
			if ( null != confirmResponse ) {
			log.info("Confirm_Delivery_Service Response Body  --->>"	+ confirmResponse.getBody().toString());
			flag = sdpservices.getConfirmationStatus(confirmResponse.getBody().toString());
			updateConfirmationServiceStatus(sdpservices, confirmResponse, master.getTransaction_id(), flag, messageCode);
			} else {
				log.info("Confirm_Delivery_Service Response is NULL ");
			}
		} catch (Exception e) {
			log.error("Error in getting the ConfirmDeliveryService Response" + e.getMessage());
			e.printStackTrace();
		}
		return confirmResponse;
	}

	/**
	 * Update confirmation service status.
	 *
	 * @param sdpservices the sdpservices
	 * @param confirmResponse the confirm response
	 * @param transactionId the transaction id
	 * @param flag the flag
	 */
	public void updateConfirmationServiceStatus(SDPServices sdpservices,
			ResponseEntity<String> confirmResponse, Long transactionId,
			boolean flag, String messageCode) {
		log.debug("Confirm_Delivery_Service Response ::" + confirmResponse.getBody());
		String interium_status = null;
		if (flag) {
			log.info("Inside the if true -- Success");
			interium_status = environment.getProperty(Constants.CONFIRM_SERVICE_SUCCESS);
		} else {
			log.info("Inside the else false -- Failure");
			interium_status = environment.getProperty(Constants.CONFIRM_SERVICE_FAILURE);
		}
		sdpservices.insertConfirmDeliveryStatus(confirmResponse.getBody().toString(), interium_status, transactionId);
		sdpservices.updateConfirmStatusMaster(interium_status, transactionId, messageCode);
	}
}