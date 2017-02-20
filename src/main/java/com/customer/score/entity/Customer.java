package com.customer.score.entity;

/**
 * Customer entity class
 * 
 */
public class Customer {
	private String name;
	private String yearOfBirth;
	private String legalId;
	private String address;
	private int score;

	public Customer(String name, String yearOfBirth, String legalId,
			String address) {
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.legalId = legalId;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public String getLegalId() {
		return legalId;
	}

	public String getAddress() {
		return address;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Customer) {

			Customer customer = (Customer) obj;

			if (this.getName().equals(customer.getName())
					&& this.getLegalId().equals(customer.getLegalId())
					&& this.getAddress().equals(customer.getAddress())
					&& this.getYearOfBirth().equals(customer.getYearOfBirth()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getLegalId().hashCode() * this.getName().hashCode()
				* this.getYearOfBirth().hashCode();
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", yearOfBirth=" + yearOfBirth
				+ ", legalId=" + legalId + ", address=" + address + "]";
	}

}
