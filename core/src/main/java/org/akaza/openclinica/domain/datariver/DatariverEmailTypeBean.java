 package org.akaza.openclinica.domain.datariver;
 
 import javax.persistence.Entity;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */
 @Entity
 @Table(name="datariver_email_type")
 public class DatariverEmailTypeBean {
 	
 	@Id
 	private Integer emailTypeId;	
 	private String description;
 	
 	/**
 	 * @return the emailTypeId
 	 */
 	public Integer getEmailTypeId() {
 		return emailTypeId;
 	}
 	
 	/**
 	 * @param emailTypeId the emailTypeId to set
 	 */
 	public void setEmailTypeId(Integer emailTypeId) {
 		this.emailTypeId = emailTypeId;
 	}
 	
 	/**
 	 * @return the description
 	 */
 	public String getDescription() {
 		return description;
 	}
 	
 	/**
 	 * @param description the description to set
 	 */
 	public void setDescription(String description) {
 		this.description = description;
 	}
 	
 }
