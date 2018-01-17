package org.akaza.openclinica.domain.datariver;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
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
@Table(name="randomization_subjects")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "randomization_subjects_id_seq") })
public class RandomizationSubjectsBean {
	@Id
	@GeneratedValue(generator = "id-generator")
	private Integer id;
	
	@Column(name="stratification_var_values")
	private String stratificationVarValues;
	
	@Column(name="date_inserted")
	private Date dateInserted;
	
	@Column(name="owner_id")
	private Integer ownerId;
	
	@Column(name="study_subject_id")
	private Integer studySubjectId;
	
	private Integer arm;

	public Integer getStudySubjectId() {
		return studySubjectId;
	}

	public void setStudySubjectId(Integer studySubjectId) {
		this.studySubjectId = studySubjectId;
	}

	public Integer getArm() {
		return arm;
	}

	public void setArm(Integer arm) {
		this.arm = arm;
	}

	
	public String getStratificationVarValues() {
		return stratificationVarValues;
	}

	public void setStratificationVarValues(String stratificationVarValues) {
		this.stratificationVarValues = stratificationVarValues;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}


