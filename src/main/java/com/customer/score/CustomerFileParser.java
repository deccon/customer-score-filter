package com.customer.score;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.customer.score.entity.Customer;
import com.opencsv.CSVReader;

public class CustomerFileParser {

	public Set<Customer> parseCustomerFileCsv(String fileName) {
		Set<Customer> customers = new HashSet<Customer>();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileName));
			String[] line;
			while ((line = reader.readNext()) != null) {
				Customer customer = convertLineToObject(line);

				if (!customers.add(customer)) {
					System.err.println("Duplicate: " + customer);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return customers;
	}

	/*
	 * Return a Customer object from the current line
	 */
	private Customer convertLineToObject(String[] customerLine) {
		String name = customerLine[1];
		String yearOfBirth = customerLine[2];
		String legalId = customerLine[3];
		String address = customerLine[4];

		return new Customer(name, yearOfBirth, legalId, address);
	}
}
