package net.javatutorial.entity;

import java.sql.Timestamp;

public class Clocking {
	private String clockingId;
    private String clockingPointName; //this clocking point names will be added to DROPDOWN table
    private String siteName;
    private Timestamp createdDt;
    private Timestamp lastModifiedDt;
    
	public Clocking(String clockingId, String clockingPointName, String siteName, Timestamp createdDt,
			Timestamp lastModifiedDt) {
		super();
		this.clockingId = clockingId;
		this.clockingPointName = clockingPointName;
		this.siteName = siteName;
		this.createdDt = createdDt;
		this.lastModifiedDt = lastModifiedDt;
	}

	public String getClockingId() {
		return clockingId;
	}

	public void setClockingId(String clockingId) {
		this.clockingId = clockingId;
	}

	public String getClockingPointName() {
		return clockingPointName;
	}

	public void setClockingPointName(String clockingPointName) {
		this.clockingPointName = clockingPointName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public Timestamp getLastModifiedDt() {
		return lastModifiedDt;
	}

	public void setLastModifiedDt(Timestamp lastModifiedDt) {
		this.lastModifiedDt = lastModifiedDt;
	}

	@Override
	public String toString() {
		return "Clocking [clockingId=" + clockingId + ", clockingPointName=" + clockingPointName + ", siteName="
				+ siteName + ", createdDt=" + createdDt + ", lastModifiedDt=" + lastModifiedDt + "]";
	}

}
