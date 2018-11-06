package org.akaza.openclinica.domain.datariver;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Enrollment Enable log bean
 * @author Fabio Benedetti
 *
 */
@Entity
@Table(name="datariver_enrollment_enable_log")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "datariver_enrollment_enable_log_id_seq") })
public class DatariverEnrollmentEnableLogBean{
	
	@Id
	@GeneratedValue(generator = "id-generator")
	private Integer Id;

	private Integer studyId;
	
	private boolean enrollmentEnabledNewStatus;
	
	private Date date;
	
	private Integer userId;
	
	
	
	public DatariverEnrollmentEnableLogBean( Date date,
			Integer userId) {
		super();
		this.date = date;
		this.userId = userId;
	}
	
	public Integer getStudyId() {
		return studyId;
	}
	
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		Id = id;
	}

	/**
	 * @return the enrollmentEnabledNewStatus
	 */
	public boolean isEnrollmentEnabledNewStatus() {
		return enrollmentEnabledNewStatus;
	}

	/**
	 * @param enrollmentEnabledNewStatus the enrollmentEnabledNewStatus to set
	 */
	public void setEnrollmentEnabledNewStatus(boolean enrollmentEnabledNewStatus) {
		this.enrollmentEnabledNewStatus = enrollmentEnabledNewStatus;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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

}
