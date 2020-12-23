package org.akaza.openclinica.domain.datariver;

import java.util.Date;

/**
 * @author DataRiver (EC) 28/11/2017
 *
 */
public class LotteryCustomBean {
	
//	lottery_id SERIAL PRIMARY KEY,
//	StudySubjectId INTEGER
//	studySubjectLabel  CHARACTER VARYING
//	siteId INTEGER
//	siteName CHARACTER VARYING
//	userId INTEGER
//	userName TEXT
//	userEmail TEXT
//	dateRegistered TIMESTAMP WITH TIME ZONE
//	outcome  CHARACTER VARYING
	
	public final static String LOTTERY_ID = "lottery_id";
	public final static String STUDY_SUBJECT_ID = "study_subject_id";
	public final static String STUDY_SUBJECT_LABEL = "study_subject_label";
	public final static String SITE_ID = "site_id";
	public final static String SITE_NAME = "site_name";
	public final static String USER_ID = "user_id";
	public final static String USER_NAME = "user_name";
	public final static String USER_EMAIL = "user_email";
	public final static String DATE_REGISTERED = "date_registered";
	public final static String OUTCOME = "outcome";


	private Integer lotteryId;
	private Integer studySubjectId;
	private String studySubjectLabel;
	private Integer siteId;
	private String siteName;
	private Integer userId;
	private String userName;
	private String userEmail;
	private Date dateRegistered;
	private String outcome;
	
	
	public Integer getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getStudySubjectId() {
		return studySubjectId;
	}
	public void setStudySubjectId(Integer studySubjectId) {
		this.studySubjectId = studySubjectId;
	}
	public String getStudySubjectLabel() {
		return studySubjectLabel;
	}
	public void setStudySubjectLabel(String studySubjectLabel) {
		this.studySubjectLabel = studySubjectLabel;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Date getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

}
