package net.javatutorial.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Visitor {
	private String vmsId;
    private String name;
    private String companyName;
    private String idType;
    private String idNo;
    private String mobileNo;
    private String vehicleNo;
    private String hostName;
    private String hostNo;
    private String visitorCardId;
    private String covidDeclare;
    private String visitPurpose;
    private String temperature;
    private Timestamp timeInDt;
    private Timestamp timeOutDt;
    
    
	public Visitor(String vmsId, String name, String companyName, String idType, String idNo, String mobileNo,
			String vehicleNo, String hostName, String hostNo, String visitorCardId, String covidDeclare,
			String visitPurpose, String temperature, Timestamp timeInDt, Timestamp timeOutDt) {
		super();
		this.vmsId = vmsId;
		this.name = name;
		this.companyName = companyName;
		this.idType = idType;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.vehicleNo = vehicleNo;
		this.hostName = hostName;
		this.hostNo = hostNo;
		this.visitorCardId = visitorCardId;
		this.covidDeclare = covidDeclare;
		this.visitPurpose = visitPurpose;
		this.temperature = temperature;
		this.timeInDt = timeInDt;
		this.timeOutDt = timeOutDt;
	}

	public Visitor(String vmsId, String name, String companyName, String idType, String idNo, String mobileNo,
			String vehicleNo, String hostName, String hostNo, String visitorCardId, String covidDeclare,
			String visitPurpose, String temperature, Timestamp timeInDt) {
		super();
		this.vmsId = vmsId;
		this.name = name;
		this.companyName = companyName;
		this.idType = idType;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.vehicleNo = vehicleNo;
		this.hostName = hostName;
		this.hostNo = hostNo;
		this.visitorCardId = visitorCardId;
		this.covidDeclare = covidDeclare;
		this.visitPurpose = visitPurpose;
		this.temperature = temperature;
		this.timeInDt = timeInDt;
	}


	/**
	 * @return the vmsId
	 */
	public String getVmsId() {
		return vmsId;
	}
	/**
	 * @param vmsId the vmsId to set
	 */
	public void setVmsId(String vmsId) {
		this.vmsId = vmsId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}
	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}
	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}
	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/**
	 * @return the hostNo
	 */
	public String getHostNo() {
		return hostNo;
	}
	/**
	 * @param hostNo the hostNo to set
	 */
	public void setHostNo(String hostNo) {
		this.hostNo = hostNo;
	}
	/**
	 * @return the visitorCardId
	 */
	public String getVisitorCardId() {
		return visitorCardId;
	}
	/**
	 * @param visitorCardId the visitorCardId to set
	 */
	public void setVisitorCardId(String visitorCardId) {
		this.visitorCardId = visitorCardId;
	}
	/**
	 * @return the covidDeclare
	 */
	public String getCovidDeclare() {
		return covidDeclare;
	}
	/**
	 * @param covidDeclare the covidDeclare to set
	 */
	public void setCovidDeclare(String covidDeclare) {
		this.covidDeclare = covidDeclare;
	}
	/**
	 * @return the visitPurpose
	 */
	public String getVisitPurpose() {
		return visitPurpose;
	}
	/**
	 * @param visitPurpose the visitPurpose to set
	 */
	public void setVisitPurpose(String visitPurpose) {
		this.visitPurpose = visitPurpose;
	}
	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the timeInDt
	 */
	public Timestamp getTimeInDt() {
		return timeInDt;
	}
	/**
	 * @param timeInDt the timeInDt to set
	 */
	public void setTimeInDt(Timestamp timeInDt) {
		this.timeInDt = timeInDt;
	}
	/**
	 * @return the timeOutDt
	 */
	public Timestamp getTimeOutDt() {
		return timeOutDt;
	}
	/**
	 * @param timeOutDt the timeOutDt to set
	 */
	public void setTimeOutDt(Timestamp timeOutDt) {
		this.timeOutDt = timeOutDt;
	}


	@Override
	public String toString() {
		return "Visitor [vmsId=" + vmsId + ", name=" + name + ", companyName=" + companyName + ", idType=" + idType
				+ ", idNo=" + idNo + ", mobileNo=" + mobileNo + ", vehicleNo=" + vehicleNo + ", hostName=" + hostName
				+ ", hostNo=" + hostNo + ", visitorCardId=" + visitorCardId + ", covidDeclare=" + covidDeclare
				+ ", visitPurpose=" + visitPurpose + ", temperature=" + temperature + ", timeInDt=" + timeInDt
				+ ", timeOutDt=" + timeOutDt + "]";
	}
	
}
