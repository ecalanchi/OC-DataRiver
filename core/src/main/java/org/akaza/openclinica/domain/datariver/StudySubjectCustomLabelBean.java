package org.akaza.openclinica.domain.datariver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Enrico Calanchi 17/dic/2014
 * Custom study subject label for parent studies
 *
 */
@Entity
@Table(name="study_subject_custom_label")
public class StudySubjectCustomLabelBean {

	private Integer studyId;
	private String labelPrefix;
	private Integer labelCodeLength;
	private String labelSuffix;
	private Integer studyLabelCounter;	
	
	/**
	 * @return the studyId
	 */
	@Id
	@Column(name = "study_id")
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
	 * @return the labelPrefix
	 */
	@Column(name = "label_prefix")
	public String getLabelPrefix() {
		return labelPrefix;
	}
	
	/**
	 * @param labelPrefix the labelPrefix to set
	 */
	public void setLabelPrefix(String labelPrefix) {
		this.labelPrefix = labelPrefix;
	}
	
	/**
	 * @return the labelCodeLength
	 */
	@Column(name = "label_code_length")
	public Integer getLabelCodeLength() {
		return labelCodeLength;
	}
	
	/**
	 * @param labelCodeLength the labelCodeLength to set
	 */
	public void setLabelCodeLength(Integer labelCodeLength) {
		this.labelCodeLength = labelCodeLength;
	}
	
	/**
	 * @return the labelSuffix
	 */
	@Column(name = "label_suffix")
	public String getLabelSuffix() {
		return labelSuffix;
	}
	
	/**
	 * @param labelSuffix the labelSuffix to set
	 */
	public void setLabelSuffix(String labelSuffix) {
		this.labelSuffix = labelSuffix;
	}
	
	/**
	 * @return the studyLabelCounter
	 */
	@Column(name = "study_label_counter")
	public Integer getStudyLabelCounter() {
		return studyLabelCounter;
	}
	
	/**
	 * @param studyLabelCounter the studyLabelCounter to set
	 */
	public void setStudyLabelCounter(Integer studyLabelCounter) {
		this.studyLabelCounter = studyLabelCounter;
	}
	
}
