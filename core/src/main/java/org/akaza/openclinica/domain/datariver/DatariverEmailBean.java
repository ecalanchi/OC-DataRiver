 package org.akaza.openclinica.domain.datariver;
 
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
 @Table(name="datariver_email")
 @GenericGenerator(name = "email-id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "datariver_email_email_id_seq") })
 public class DatariverEmailBean {
 	
 	@Id
 	@GeneratedValue(generator = "email-id-generator")
 	private Integer emailId;	
 	private String subject;
 	private String recipients;
 	private String htmlBody;
 	private String sender;
 	private String bcc;
 	private String replyTo;
 	private Integer emailTypeId;
 	private String attachmentPath;
 	private Integer studyId;
 	private Integer eventDefinitionCrfId;
 	private Integer crfId;
 	private Integer studyGroupId;
 	private Boolean enabled;
 	
 	
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
 	 * @return the htmlBody
 	 */
 	public String getHtmlBody() {
 		return htmlBody;
 	}
 
 	/**
 	 * @param htmlBody the htmlBody to set
 	 */
 	public void setHtmlBody(String htmlBody) {
 		this.htmlBody = htmlBody;
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
 
 	/**
 	 * @return the replyTo
 	 */
 	public String getReplyTo() {
 		return replyTo;
 	}
 
 	/**
 	 * @param replyTo the replyTo to set
 	 */
 	public void setReplyTo(String replyTo) {
 		this.replyTo = replyTo;
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
 	 * @return the attachmentPath
 	 */
 	public String getAttachmentPath() {
 		return attachmentPath;
 	}
 
 	/**
 	 * @param attachmentPath the attachmentPath to set
 	 */
 	public void setAttachmentPath(String attachmentPath) {
 		this.attachmentPath = attachmentPath;
 	}
 
 	/**
 	 * @return the studyId
 	 */
 	public Integer getStudyId() {
 		return studyId;
 	}
 
 	/**
 	 * @param studyId the studyId to set
 	 */
 	public void setStudyId(Integer studyId) {
 		this.studyId = studyId;
 	}
 
 	/**
 	 * @return the eventDefinitionCrfId
 	 */
 	public Integer getEventDefinitionCrfId() {
 		return eventDefinitionCrfId;
 	}
 
 	/**
 	 * @param eventDefinitionCrfId the eventDefinitionCrfId to set
 	 */
 	public void setEventDefinitionCrfId(Integer eventDefinitionCrfId) {
 		this.eventDefinitionCrfId = eventDefinitionCrfId;
 	}
 
 	/**
 	 * @return the crfId
 	 */
 	public Integer getCrfId() {
 		return crfId;
 	}
 
 	/**
 	 * @param crfId the crfId to set
 	 */
 	public void setCrfId(Integer crfId) {
 		this.crfId = crfId;
 	}
 
 	/**
 	 * @return the studyGroupId
 	 */
 	public Integer getStudyGroupId() {
 		return studyGroupId;
 	}
 
 	/**
 	 * @param studyGroupId the studyGroupId to set
 	 */
 	public void setStudyGroupId(Integer studyGroupId) {
 		this.studyGroupId = studyGroupId;
 	}
 
 	/**
 	 * @return the enabled
 	 */
 	public Boolean getEnabled() {
 		return enabled;
 	}
 
 	/**
 	 * @param enabled the enabled to set
 	 */
 	public void setEnabled(Boolean enabled) {
 		this.enabled = enabled;
 	}
 
 }
