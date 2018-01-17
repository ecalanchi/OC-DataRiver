 package org.akaza.openclinica.domain.datariver;
 
 import java.util.Date;
 
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 import org.hibernate.annotations.GenericGenerator;
 import org.hibernate.annotations.Parameter;
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */
 @Entity
 @Table(name="datariver_email_log")
 @GenericGenerator(name = "email-log-id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "datariver_email_log_email_log_id_seq") })
 public class DatariverEmailLogBean {
 
 	@Id
 	@GeneratedValue(generator = "email-log-id-generator")
 	private Integer emailLogId;
 	private Integer userId;
 	private Date dateSent;
 	private String deliveryStatus;
 	private String htmlBodyFilepath;
 	private Integer emailId;
 	private Integer emailTypeId;
 	private String subject;
 	private String recipients;
 	private String sender;
 	private String bcc;
 	
 	public DatariverEmailLogBean(Integer userId, Date dateSent, String deliveryStatus, String htmlBodyFilepath, Integer emailId, Integer emailTypeId, String subject, String recipients, String sender, String bcc) {
 		super();
 		this.userId = userId;
 		this.dateSent = dateSent;
 		this.deliveryStatus = deliveryStatus;
 		this.htmlBodyFilepath = htmlBodyFilepath;
 		this.emailId = emailId;
 		this.emailTypeId = emailTypeId;
 		this.subject = subject;
 		this.recipients = recipients;
 		this.sender = sender;
 		this.bcc = bcc;
 	}
 	
 	public DatariverEmailLogBean(Date dateSent, Integer userId, Integer emailId, String deliveryStatus) {
 		super();
 		this.dateSent = dateSent;
 		this.userId = userId;
 		this.emailId = emailId;
 		this.deliveryStatus = deliveryStatus;
 	}
 	
 	public DatariverEmailLogBean() {
 		super();
 	}
 	
 	
 	/**
 	 * @return the emailLogId
 	 */
 	public Integer getEmailLogId() {
 		return emailLogId;
 	}
 	
 	/**
 	 * @param emailLogId the emailLogId to set
 	 */
 	public void setEmailLogId(Integer emailLogId) {
 		this.emailLogId = emailLogId;
 	}
 	
 	/**
 	 * @return the userId
 	 */
 	public Integer getUserId() {
 		return userId;
 	}
 	
 	/**
 	 * @param userId the userId to set
 	 */
 	public void setUserId(Integer userId) {
 		this.userId = userId;
 	}
 	
 	/**
 	 * @return the dateSent
 	 */
 	public Date getDateSent() {
 		return dateSent;
 	}
 	
 	/**
 	 * @param dateSent the dateSent to set
 	 */
 	public void setDateSent(Date dateSent) {
 		this.dateSent = dateSent;
 	}
 	
 	/**
 	 * @return the deliveryStatus
 	 */
 	public String getDeliveryStatus() {
 		return deliveryStatus;
 	}
 
 
 	/**
 	 * @param deliveryStatus the deliveryStatus to set
 	 */
 	public void setDeliveryStatus(String deliveryStatus) {
 		this.deliveryStatus = deliveryStatus;
 	}
 
 
 	/**
 	 * @return the htmlBodyFilepath
 	 */
 	public String getHtmlBodyFilepath() {
 		return htmlBodyFilepath;
 	}
 	
 	/**
 	 * @param htmlBodyFilepath the htmlBodyFilepath to set
 	 */
 	public void setHtmlBodyFilepath(String htmlBodyFilepath) {
 		this.htmlBodyFilepath = htmlBodyFilepath;
 	}
 	
 	/**
 	 * @return the emailId
 	 */
 	public Integer getEmailId() {
 		return emailId;
 	}
 	
 	/**
 	 * @param emailId the emailId to set
 	 */
 	public void setEmailId(Integer emailId) {
 		this.emailId = emailId;
 	}
 	
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
 	 * @return the subject
 	 */
 	public String getSubject() {
 		return subject;
 	}
 	
 	/**
 	 * @param subject the subject to set
 	 */
 	public void setSubject(String subject) {
 		this.subject = subject;
 	}
 	
 	/**
 	 * @return the recipients
 	 */
 	public String getRecipients() {
 		return recipients;
 	}
 	
 	/**
 	 * @param recipients the recipients to set
 	 */
 	public void setRecipients(String recipients) {
 		this.recipients = recipients;
 	}
 	
 	/**
 	 * @return the sender
 	 */
 	public String getSender() {
 		return sender;
 	}
 	
 	/**
 	 * @param sender the sender to set
 	 */
 	public void setSender(String sender) {
 		this.sender = sender;
 	}
 	
 	/**
 	 * @return the bcc
 	 */
 	public String getBcc() {
 		return bcc;
 	}
 	
 	/**
 	 * @param bcc the bcc to set
 	 */
 	public void setBcc(String bcc) {
 		this.bcc = bcc;
 	}
 
 }
