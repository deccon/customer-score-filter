package com.customer.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.customer.score.entity.Customer;

/**
 * CustomerFilter application to process a customer csv file and
 * produce a list of customers who are in the top 3% based on their scores 
 */
public class CustomerFilter {

	private CustomerFileHelper customerFileHelper = new CustomerFileHelper();

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();

		CustomerFilter customerFilter = new CustomerFilter();
		Set<Customer> allCustomers = new HashSet<Customer>();

		if (args.length > 0) {

			String fileName = args[0];
			allCustomers = customerFilter.processCSVFile(fileName);

			customerFilter.processCustomerScores(allCustomers);
			customerFilter.writeHighScoringCustomers(allCustomers);
		} else {
			System.err.println("Please supply a file as first argument");
		}
		
		long end = System.currentTimeMillis();
		
		customerFilter.printTotalTimeMinutes(start, end);
	}

	/*
	 * Print out the customers whose score fall in top 3%
	 */
	private void writeHighScoringCustomers(Set<Customer> allCustomers) {
		List<Customer> customers = sortHighScoringCustomers(allCustomers);

		List<Customer> topScoringCustomers = new ArrayList<Customer>();

		// get top 3% of scores
		double threePrecent = (customers.size() * 0.03);

		System.out.println("Top 3% of high scores: ");

		for (int i = 0; i <= threePrecent; i++) {
			topScoringCustomers.add(customers.get(i));
			System.out.println(customers.get(i));
		}

	}

	private Set<Customer> processCSVFile(String fileName) {
		return customerFileHelper.parseCustomerFileCsv(fileName);
	}

	private void processCustomerScores(Set<Customer> allCustomers) {
		CustomerScoreCalculator scoreCalculator = new CustomerScoreCalculator();

		allCustomers = scoreCalculator.populateCustomerScores(allCustomers);
	}

	/*
	 * Return a list of customers sorted by highest score
	 */
	private List<Customer> sortHighScoringCustomers(Set<Customer> allCustomers) {

		List<Customer> customerList = new ArrayList<>();
		customerList.addAll(allCustomers);

		Collections.sort(customerList, new Comparator<Customer>() {
			@Override
			public int compare(Customer cust1, Customer cust2) {
				return cust1.getScore() < cust2.getScore() ? 1 : cust1
						.getScore() > cust2.getScore() ? -1 : 0;
			}
		});

		return customerList;
	}

	/*
	 * Print total time taken to process customer file
	 */
	private void printTotalTimeMinutes(long start, long end) {
		long totalMillis = end - start;
		double secs = totalMillis / 1000.0;
		double mins = secs / 60.0;

		System.out.println("Time (mins): " + String.format( "%.2f", mins));
	}
}
