package net.javatutorial.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class ClientAccount {
	//https://www.postgresql.org/docs/8.4/pgcrypto.html
	//https://x-team.com/blog/storing-secure-passwords-with-postgresql/
	private String accountId;
    private String name;
    private String idType;
    private String idNo;
    private String password;
    private Timestamp createdDt;
    private Timestamp modifiedDt;
    
	public ClientAccount(String accountId, String name, String idType, String idNo, String password,
			Timestamp createdDt, Timestamp modifiedDt) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.idType = idType;
		this.idNo = idNo;
		this.password = password;
		this.createdDt = createdDt;
		this.modifiedDt = modifiedDt;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the createdDt
	 */
	public Timestamp getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createdDt the createdDt to set
	 */
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the modifiedDt
	 */
	public Timestamp getModifiedDt() {
		return modifiedDt;
	}

	/**
	 * @param modifiedDt the modifiedDt to set
	 */
	public void setModifiedDt(Timestamp modifiedDt) {
		this.modifiedDt = modifiedDt;
	}

	@Override
	public String toString() {
		return "ClientAccount [accountId=" + accountId + ", name=" + name + ", idType=" + idType + ", idNo=" + idNo
				+ ", password=" + password + ", createdDt=" + createdDt + ", modifiedDt=" + modifiedDt + "]";
	}
    
    
	
}
