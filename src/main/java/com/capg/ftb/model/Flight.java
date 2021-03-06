/* 
 * Author:Mrudhula
 * 
 */

package com.capg.ftb.model;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name="Flight")
@ApiModel(value="Flight entity")
public class Flight 
{
	
	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "Autogenerated value", required = true, position=1)
	private BigInteger flightNumber;
	
	
	@Column
	@ApiModelProperty(notes = "Carrier name cannot be null minimum 3 letters", required = false, position=2)
    private String carrierName; 
	
	
	@Column
	@ApiModelProperty(notes = "Flight can not be null and minimum three letters", required = false, position=3)
	private String flightModel;
	
	
	@Column
	@ApiModelProperty(notes = "Minimum of 30 seats should be available", required = false, position=4)
	private int seatCapacity;
	
		
	public Flight()
	{
		super();
	}
	
	public Flight(BigInteger flightNumber,String carrierName, String flightModel, Integer seatCapacity) 
	{
		super();
		this.flightNumber = flightNumber;
		this.carrierName = carrierName;
		this.flightModel = flightModel;
		this.seatCapacity = seatCapacity;
	}
	
	public BigInteger getFlightNumber()
	{
		return flightNumber;
	}
	
	public void setFlightNumber(BigInteger flightNumber) 
	{
		this.flightNumber = flightNumber;
	}
	
	public String getCarrierName()
	{
		return carrierName;
	}
	
	public void setCarrierName(String carrierName)
	{
		this.carrierName = carrierName;
	}
	
	public String getFlightModel()
	{
		return flightModel;
	}
	
	public void setFlightModel(String flightModel) 
	{
		this.flightModel = flightModel;
	}
	
	public Integer getSeatCapacity() 
	{
		return seatCapacity;
	}
	
	public void setSeatCapacity(Integer seatCapacity) 
	{
		this.seatCapacity = seatCapacity;
	}
	

}

