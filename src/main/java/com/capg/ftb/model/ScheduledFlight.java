package com.capg.ftb.model;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
//import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@ApiModel(value="Scheduled Flights Enity")
public class ScheduledFlight {

	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "Autogenerated value", required = true, position=1)
	private BigInteger scheduleFlightId;

	@OneToOne(targetEntity=Flight.class,cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="flightNumber")
	@ApiModelProperty(notes = "One to One mapping to Flight entity", required = false, position=2)
	private Flight flight;

	@Column(name = "availableseats")
	@ApiModelProperty(notes = "Available seats to book", required = false, position=3)
	private Integer availableSeats;


	@OneToOne(targetEntity=Schedule.class,cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName="scheduleId")
	@ApiModelProperty(notes = "OnetoOne relation between Schedule", required = false, position=4)
	private Schedule schedule;
	
	@NotNull
	@Min(value=(long) 500.0,message="minimum cost 500.0")
	@ApiModelProperty(notes = "Minimum of 500 per person", required = false, position=5)
	private double costPerHead;

	public double getCostPerHead() {
		return costPerHead;
	}

	public void setCostPerHead(double costPerHead) {
		this.costPerHead = costPerHead;
	}

	
	/*
	 * Default constructor
	 */
	public ScheduledFlight() {

	}

	/*
	 * Parameterized constructor
	 */
	public ScheduledFlight(BigInteger scheduleFlightId, Flight flight, Integer availableSeats,
			Schedule schedule,double costPerHead) {
		super();
		this.scheduleFlightId = scheduleFlightId;
		this.flight = flight;
		this.availableSeats = availableSeats;
		this.schedule = schedule;
		this.costPerHead=costPerHead;
	}

	/*
	 * Getter and setter for ID
	 */
	public BigInteger getScheduleFlightId() {
		return scheduleFlightId;
	}

	public void setScheduleFlightId(BigInteger scheduleFlightId) {
		this.scheduleFlightId = scheduleFlightId;
	}

	/*
	 * Getter and setter for Available seats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	/*
	 * Getter and setter for Flight object
	 */
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	/*
	 * Getter and setter for Schedule object
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
