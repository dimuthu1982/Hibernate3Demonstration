package home.hibernate.dto;

import javax.persistence.Column;

public class OfficeContactDetails {

	@Column(name ="MOBILE_NUMBER")
	private int mobileNumber;
	
	@Column(name ="LAND_LINE_NUMBER")
	private int landLineNumber;

	
	public OfficeContactDetails(int mobileNumber, int landLineNumber) {
		this.mobileNumber = mobileNumber;
		this.landLineNumber = landLineNumber;
	}

	public OfficeContactDetails() {
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public int getLandLineNumber() {
		return landLineNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setLandLineNumber(int landLineNumber) {
		this.landLineNumber = landLineNumber;
	}
}
