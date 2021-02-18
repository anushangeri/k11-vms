package net.javatutorial.entity;

import java.sql.Timestamp;

public class Vehicle {
	private String vehicleId;
    private String name;
    private String companyName;
    private String idType;
    private String idNo;
    private String mobileNo;
    private String primeMoverNo;
    private String containerNo;
    private String loadedNoLoaded;
    private String covidDeclare;
    private String lorryChetNumber;
    private String deliveryNoticeNumber;
    private String visitPurpose;
    private String temperature;
    private Timestamp timeInDt;
    private Timestamp timeOutDt;
    

	public Vehicle(String vehicleId, String name, String companyName, String idType, String idNo, String mobileNo,
			String primeMoverNo, String containerNo, String loadedNoLoaded, String covidDeclare, String lorryChetNumber,
			String deliveryNoticeNumber, String visitPurpose, String temperature, Timestamp timeInDt,
			Timestamp timeOutDt) {
		super();
		this.vehicleId = vehicleId;
		this.name = name;
		this.companyName = companyName;
		this.idType = idType;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.primeMoverNo = primeMoverNo;
		this.containerNo = containerNo;
		this.loadedNoLoaded = loadedNoLoaded;
		this.covidDeclare = covidDeclare;
		this.lorryChetNumber = lorryChetNumber;
		this.deliveryNoticeNumber = deliveryNoticeNumber;
		this.visitPurpose = visitPurpose;
		this.temperature = temperature;
		this.timeInDt = timeInDt;
		this.timeOutDt = timeOutDt;
	}

	public Vehicle(String vehicleId, String name, String companyName, String idType, String idNo, String mobileNo,
			String primeMoverNo, String containerNo, String loadedNoLoaded, String covidDeclare, String lorryChetNumber,
			String deliveryNoticeNumber, String visitPurpose, String temperature, Timestamp timeInDt) {
		super();
		this.vehicleId = vehicleId;
		this.name = name;
		this.companyName = companyName;
		this.idType = idType;
		this.idNo = idNo;
		this.mobileNo = mobileNo;
		this.primeMoverNo = primeMoverNo;
		this.containerNo = containerNo;
		this.loadedNoLoaded = loadedNoLoaded;
		this.covidDeclare = covidDeclare;
		this.lorryChetNumber = lorryChetNumber;
		this.deliveryNoticeNumber = deliveryNoticeNumber;
		this.visitPurpose = visitPurpose;
		this.temperature = temperature;
		this.timeInDt = timeInDt;
	}
	
	/**
	 * @return the vehicleId
	 */
	public String getVehicleId() {
		return vehicleId;
	}
	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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
	 * @return the primeMoverNo
	 */
	public String getPrimeMoverNo() {
		return primeMoverNo;
	}
	/**
	 * @param primeMoverNo the primeMoverNo to set
	 */
	public void setPrimeMoverNo(String primeMoverNo) {
		this.primeMoverNo = primeMoverNo;
	}
	/**
	 * @return the containerNo
	 */
	public String getContainerNo() {
		return containerNo;
	}
	/**
	 * @param containerNo the containerNo to set
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	/**
	 * @return the loadedNoLoaded
	 */
	public String getLoadedNoLoaded() {
		return loadedNoLoaded;
	}
	/**
	 * @param loadedNoLoaded the loadedNoLoaded to set
	 */
	public void setLoadedNoLoaded(String loadedNoLoaded) {
		this.loadedNoLoaded = loadedNoLoaded;
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
	 * @return the lorryChetNumber
	 */
	public String getLorryChetNumber() {
		return lorryChetNumber;
	}
	/**
	 * @param lorryChetNumber the lorryChetNumber to set
	 */
	public void setLorryChetNumber(String lorryChetNumber) {
		this.lorryChetNumber = lorryChetNumber;
	}
	/**
	 * @return the deliveryNoticeNumber
	 */
	public String getDeliveryNoticeNumber() {
		return deliveryNoticeNumber;
	}
	/**
	 * @param deliveryNoticeNumber the deliveryNoticeNumber to set
	 */
	public void setDeliveryNoticeNumber(String deliveryNoticeNumber) {
		this.deliveryNoticeNumber = deliveryNoticeNumber;
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
		return "Vehicle [vehicleId=" + vehicleId + ", name=" + name + ", companyName=" + companyName + ", idType="
				+ idType + ", idNo=" + idNo + ", mobileNo=" + mobileNo + ", primeMoverNo=" + primeMoverNo
				+ ", containerNo=" + containerNo + ", loadedNoLoaded=" + loadedNoLoaded + ", covidDeclare="
				+ covidDeclare + ", lorryChetNumber=" + lorryChetNumber + ", deliveryNoticeNumber="
				+ deliveryNoticeNumber + ", visitPurpose=" + visitPurpose + ", temperature=" + temperature
				+ ", timeInDt=" + timeInDt + ", timeOutDt=" + timeOutDt + "]";
	}
	
}
