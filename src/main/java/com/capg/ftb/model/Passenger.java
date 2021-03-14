/*
 * Mary Jennifer
 */
package com.capg.ftb.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;

@Entity
@Table(name = "PASSENGERS")
@ApiModel(value="Passenger entity")
public class Passenger {

	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "Autogenerated value", required = true, position=1)
	private BigInteger pnrNumber;

	@Column(name = "passengerName")
	@ApiModelProperty(notes = "Passenger name cannot be null", required = false, position=2)
	private String passengerName;

	@Column(name = "passengerAge")
	@ApiModelProperty(notes = "Age of a Pasenger", required = false, position=3)
	private int passengerAge;

	@Column
	@ApiModelProperty(notes = "Fixed length of 12 digits", required = false, position=4)
	private BigInteger passengerUIN;

	@Column(name = "Luggage")
	@ApiModelProperty(notes = "Passenger luggage", required = false, position=5)
	private Double Luggage;

	public BigInteger getpnrNumber() {
		return pnrNumber;
	}

	public void setpnrNumber(BigInteger pnrNumber) {
		this.pnrNumber = pnrNumber;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int getPassengerAge() {
		return passengerAge;
	}

	public void setPassengerAge(int passengerAge) {
		this.passengerAge = passengerAge;
	}

	public  BigInteger getPassengerUIN() {
		return passengerUIN;
	}

	public void setPassengerUIN(BigInteger passengerUIN) {
		this.passengerUIN = passengerUIN;
	}

	public Double getLuggage() {
		return Luggage;
	}

	public void setLuggage(Double Luggage) {
		this.Luggage = Luggage;
	}
	
	public Passenger() {
		super();
	}
	
	public Passenger(BigInteger pnrNumber, String passengerName,int passengerAge, BigInteger passengerUIN, Double Luggage) {
		super();
		this.pnrNumber = pnrNumber;
		this.passengerName = passengerName;
		this.passengerAge = passengerAge;
		this.passengerUIN = passengerUIN;
		this.Luggage = Luggage;
	}
}
