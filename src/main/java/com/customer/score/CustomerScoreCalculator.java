package com.customer.score;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.customer.score.entity.Customer;

import static com.customer.score.CustomerScoreConstants.CUSTOMER_SCORE_URL;

/**
 * CustomerScoreCalculator will connect to remote service to retrieve the customer score 
 *
 */
public class CustomerScoreCalculator {

	public void sendCustomerScoreRequest(Customer customer) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPost request = new HttpPost(CUSTOMER_SCORE_URL);
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Authorization", "APIKEY ha861ba6rds7d");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		try {
		    String json = objectMapper.writeValueAsString(customer);
            System.out.println(json);
		    request.setEntity(new StringEntity(json));
			HttpResponse response = httpClient.execute(request);
			
			InputStream is = response.getEntity().getContent();
			System.out.println("status:" + response.getStatusLine().getStatusCode());
			System.out.println("<" + is.toString());
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String response1 = is.toString();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
