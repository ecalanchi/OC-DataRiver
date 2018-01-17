package org.akaza.openclinica.domain.datariver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




/**
 * @author DataRiver (EC) 28/11/2017
 *
 */
@Entity
@Table(name="randomization_var")
public class RandomizationVarBean {
	
	@Id
	@Column(name="study_group_class_id")
	private Integer studyGroupClassId;

	@Column(name="study_event_definition_id")
	private Integer studyEventDefinitionId;
	
	@Column(name="crf_id")
	private Integer crfId;
	
	@Column(name="stratification_var_ids")
	private String stratificationVarIds;
	
	@Column(name="randomization_list")
	private String randomizationList;
	
	public Integer getStudyGroupClassId() {
		return studyGroupClassId;
	}

	public void setStudyGroupClassId(Integer studyGroupClassId) {
		this.studyGroupClassId = studyGroupClassId;
	}

	public Integer getStudyEventDefinitionId() {
		return studyEventDefinitionId;
	}

	public void setStudyEventDefinitionId(Integer studyEventDefinitionId) {
		this.studyEventDefinitionId = studyEventDefinitionId;
	}

	public Integer getCrfId() {
		return crfId;
	}

	public void setCrfId(Integer crfId) {
		this.crfId = crfId;
	}

	public String getStratificationVarIds() {
		return stratificationVarIds;
	}

	public void setStratificationVarIds(String stratificationVarIds) {
		this.stratificationVarIds = stratificationVarIds;
	}

	public String getRandomizationList() {
		return randomizationList;
	}

	public void setRandomizationList(String randomizationList) {
		this.randomizationList = randomizationList;
	}

	
	
	

}
