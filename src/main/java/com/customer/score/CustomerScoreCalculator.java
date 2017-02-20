package com.customer.score;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

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
		
		List<NameValuePair> postBody = setPostBody(customer);
		
		
		try {
			request.setEntity(new UrlEncodedFormEntity(postBody));
			HttpResponse response = httpClient.execute(request);
			
			System.out.println(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<NameValuePair> setPostBody(Customer customer) {
		List<NameValuePair> body = new ArrayList<NameValuePair>();
		
		body.add(new BasicNameValuePair("name", customer.getName()));
		body.add(new BasicNameValuePair("yob", customer.getYearOfBirth()));
		body.add(new BasicNameValuePair("legalID", customer.getLegalId()));
		body.add(new BasicNameValuePair("address", customer.getAddress()));
		
		return body;
	}
}
