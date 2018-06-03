package com.hpe.payment.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * The Class RestClient.
 * 
 * @author Prasad.Depani
 * @date 06-09-2017
 * @version 1.0
 * 
 */
@PropertySource("classpath:application.properties")
public class RestClient {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(RestClient.class);

	/** The env. */
	Environment env;
	
	/**
	 * Gets the transaction status.
	 *
	 * @param transactionId the transaction id
	 * @return the transaction status
	 */
	public String getTransactionStatus(Long transactionId) {
		log.info("Inside class: RestClient ::  method: getTransactionStatus -> Starts");
		String response = null;
		try {
			RestTemplate restTemplate = new RestTemplate();						
			//String URI = "http://172.28.44.105:8080/transactionclient/getTransactionStatus/"+transactionId;
			String URI = "http://localhost:8080/transactionclient/getTransactionStatus/"+transactionId;
			log.info("URI ---->>" + URI);			
			response = restTemplate.getForObject(URI, String.class, transactionId);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("Inside class: RestClient ::  method: getTransactionStatus -> Ends");
		return response;
	}

	/**
	 * Gets the confirmation service.
	 *
	 * @param spCode the sp code
	 * @param servCode the serv code
	 * @param sptrn the sptrn
	 * @param code the code
	 * @param message the message
	 * @return the confirmation service
	 */
	public ResponseEntity<String> getConfirmationService(String spCode, String servCode, String degTrn, String code,
			String message) 
			

			{
		log.info("Inside class: RestClient ::  method: getConfirmationService -> Starts" +degTrn +"--" +code +"---"  +message );
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();		
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("spCode", spCode);
		inputMap.put("servCode", servCode);
		String sptransNo = degTrn;
		String sptransNopadding = String.format("%0" + (25-sptransNo.length()) + "d%s", 0, sptransNo); 
		inputMap.put("sptrn", sptransNopadding);
		inputMap.put("code", code);
		inputMap.put("message", message);

		try {
			log.info("Inside class: RestClient ::  inputMap..--->" +inputMap);
			//response = restTemplate.postForEntity("http://172.28.44.105:8080/PaymentIntegration/confirm_service_delivery/",inputMap, String.class);
			response = restTemplate.postForEntity("http://localhost:8080/PaymentIntegration/confirm_service_delivery/",inputMap, String.class);
		} catch (Exception e) {
			log.error("Exception in getting confirmation respose" + e.getMessage());
		}
		log.info("Inside class: RestClient ::  method: getConfirmationService -> Ends");
		return response;
	}
}