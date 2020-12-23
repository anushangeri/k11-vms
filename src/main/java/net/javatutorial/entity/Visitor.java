package net.javatutorial.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Visitor {
	private String vmsId;
    private String firstName;
    private String lastName;
    private String idNo;
    private String mobileNo;
    private String vehicleNo;
    private String hostName;
    private String hostNo;
    private String visitorCardId;
    private Timestamp timeInDt;
    private Timestamp timeOutDt;
    
	public Visitor(String vmsId, String firstName, String lastName, String idNo, String mobileNo, String vehicleNo,
			String hostName, String hostNo, String visitorCardId, Timestamp timeInDt, Timestamp timeOutDt) {
		super();
		this.vmsId = vmsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.vehicleNo = vehicleNo;
		this.hostName = hostName;
		this.hostNo = hostNo;
		this.visitorCardId = visitorCardId;
		this.timeInDt = timeInDt;
		this.timeOutDt = timeOutDt;
	}

	public Visitor(String vmsId, String firstName, String lastName, String idNo, String mobileNo, String vehicleNo,
			String hostName, String hostNo, String visitorCardId, Timestamp timeInDt) {
		super();
		this.vmsId = vmsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.vehicleNo = vehicleNo;
		this.hostName = hostName;
		this.hostNo = hostNo;
		this.visitorCardId = visitorCardId;
		this.timeInDt = timeInDt;
	}

	public String getVmsId() {
		return vmsId;
	}

	public void setVmsId(String vmsId) {
		this.vmsId = vmsId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostNo() {
		return hostNo;
	}

	public void setHostNo(String hostNo) {
		this.hostNo = hostNo;
	}

	public String getVisitorCardId() {
		return visitorCardId;
	}

	public void setVisitorCardId(String visitorCardId) {
		this.visitorCardId = visitorCardId;
	}

	public Timestamp getTimeInDt() {
		return timeInDt;
	}

	public void setTimeInDt(Timestamp timeInDt) {
		this.timeInDt = timeInDt;
	}

	public Timestamp getTimeOutDt() {
		return timeOutDt;
	}

	public void setTimeOutDt(Timestamp timeOutDt) {
		this.timeOutDt = timeOutDt;
	}

	@Override
	public String toString() {
		return "Visitor [vmsId=" + vmsId + ", firstName=" + firstName + ", lastName=" + lastName + ", idNo=" + idNo
				+ ", mobileNo=" + mobileNo + ", vehicleNo=" + vehicleNo + ", hostName=" + hostName + ", hostNo="
				+ hostNo + ", visitorCardId=" + visitorCardId + ", timeInDt=" + timeInDt + ", timeOutDt=" + timeOutDt
				+ "]";
	}
    
}
