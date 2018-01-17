package org.akaza.openclinica.domain.datariver;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DataRiver (EC) 28/11/2017
 *
 */
@Entity
@Table(name="randomization_arms")
public class RandomizationArmsBean {
	@Id
	private Integer arm;
	
	private Integer ratio;
	
	private Integer studyGroupClassId;

	public Integer getArm() {
		return arm;
	}

	public void setArm(Integer arm) {
		this.arm = arm;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public Integer getStudyGroupClassId() {
		return studyGroupClassId;
	}

	public void setStudyGroupClassId(Integer studyGroupClassId) {
		this.studyGroupClassId = studyGroupClassId;
	}

}
