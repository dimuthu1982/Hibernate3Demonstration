package home.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "WORK_STATIONS")
public class Laptop {
	
	@Column(name = "WORK_STATION_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int laptopId;

	@Column(name = "MODEL_ID")
	private String modelId;

	@Column(name = "NAME")
	private String name;

	public Laptop() {
	}

	public Laptop(String modelId, String name) {
		this.modelId = modelId;
		this.name = name;
	}

	public int getLaptopId() {
		return laptopId;
	}

	public void setLaptopId(int laptopId) {
		this.laptopId = laptopId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
