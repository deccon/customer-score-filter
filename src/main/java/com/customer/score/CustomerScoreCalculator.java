package com.customer.score;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.customer.score.entity.Customer;
import com.customer.score.entity.CustomerScore;

import static com.customer.score.CustomerScoreConstants.*;

/**
 * CustomerScoreCalculator will connect to remote service to retrieve the
 * customer score
 * 
 */
public class CustomerScoreCalculator {

	/**
	 * Retrieve and populate all customer scores
	 * 
	 * @param allCustomers
	 * @return {@link Set} of all customers
	 */
	public Set<Customer> populateCustomerScores(Set<Customer> allCustomers) {
		
		for (Customer customer: allCustomers) {
			customer = populateCustomerScore(customer);
		}

		return allCustomers;
	}

	/**
	 * Retrieve and populate customer score
	 * 
	 * @param customer
	 * @return {@link Customer} object with score
	 */
	public Customer populateCustomerScore(Customer customer) {
		CustomerScore custScore = sendCustomerScoreRequest(customer);
		customer.setScore(custScore.getScore());
		return customer;
	}

	/**
	 * Build and submit a {@link HttpPost} request to get the customer score
	 * 
	 * @param customer
	 * @return {@link CustomerScore} object
	 */
	public CustomerScore sendCustomerScoreRequest(Customer customer) {
		
		//set request timeout of 500 millisecs
	    RequestConfig conf = RequestConfig.custom().setConnectTimeout(500).build();
	    
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(conf).build();
		HttpPost request = buildCustomerScoreRequest(customer);
		CustomerScore customerScore = new CustomerScore();
		
		try {
			HttpResponse response = httpClient.execute(request);
			customerScore = getCustomerScore(response);
	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerScore;
	}

	/*
	 * Build HttpPost request to send toward customer score service
	 */
	private HttpPost buildCustomerScoreRequest(Customer customer) {
		HttpPost request = new HttpPost(CUSTOMER_SCORE_URL);
	
		request.addHeader("Content-Type", CONTENT_TYPE_JSON);
		request.addHeader("Authorization", CUSTOMER_AUTHORIZATION_KEY);
	
		ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(customer);
			request.setEntity(new StringEntity(json));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		return request;
	}

	/*
	 * Get the CustomerScore object from a response
	 */
	private CustomerScore getCustomerScore(HttpResponse response) {
		CustomerScore customerScore = null;
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream is;
		try {
			is = response.getEntity().getContent();
			customerScore = objectMapper.readValue(is, CustomerScore.class);
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return customerScore;
	}
}
