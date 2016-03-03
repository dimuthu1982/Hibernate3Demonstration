package home.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VehiclePass {
	
	@Column(name="VEHICLE_NUMBER")
	private String vehicleNumber;

	public VehiclePass() {
	}

	public VehiclePass(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}
	
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
}
