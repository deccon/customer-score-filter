package com.customer.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Customer entity class
 */
public class Customer {
    
    @JsonProperty("name")
	private String name;
	
	@JsonProperty("yob")
	private String yearOfBirth;
	
	@JsonProperty("legalID")
	private String legalId;
	
	@JsonProperty("address")
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
        this.score = score;;
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
		return this.getName().hashCode() * this.getLegalId().hashCode();
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", yearOfBirth=" + yearOfBirth
				+ ", legalId=" + legalId + ", address=" + address + ", score=" + score + "]";
	}

}
