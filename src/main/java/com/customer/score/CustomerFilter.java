package com.customer.score;

import java.util.Set;

import com.customer.score.entity.Customer;

public class CustomerFilter {

	public static void main(String[] args) {
		
		CustomerFilter filter = new CustomerFilter();
		Set<Customer> allCustomers;

		long start = System.currentTimeMillis();

		if (args.length > 0) {

			String fileName = args[0];
			allCustomers = filter.processCSVFile(fileName);
			filter.processCustomerScores(allCustomers);
		}

		long end = System.currentTimeMillis();

		System.out.println("total: " + (end - start));

	}

	private void processCustomerScores(Set<Customer> allCustomers) {
		CustomerScoreCalculator scoreCalculator = new CustomerScoreCalculator();
		
		for(Customer customer: allCustomers){			
			scoreCalculator.sendCustomerScoreRequest(customer);
		}
		
	}

	private Set<Customer> processCSVFile(String fileName) {
		
		CustomerFileParser parser = new CustomerFileParser();
		
		// Read in customers to objects
		return parser.parseCustomerFileCsv(fileName);
	}

}
