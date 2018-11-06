package org.akaza.openclinica.domain.datariver;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Enrollment Enable bean
 * @author Fabio Benedetti
 *
 */
@Entity
@Table(name="datariver_enrollment_enable")
public class DatariverEnrollmentEnableBean{
	@Id
	private Integer studyId;
	
	private boolean enable;
	
	
	public Integer getStudyId() {
		return studyId;
	}
	
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
