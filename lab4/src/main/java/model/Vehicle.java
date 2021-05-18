package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mark;
	private int seatsCount;
	private long unitId;

	@Override
	public String toString(){
		return "Vehicle with mark: " + mark + " and seats count = " + seatsCount;
	}
}
