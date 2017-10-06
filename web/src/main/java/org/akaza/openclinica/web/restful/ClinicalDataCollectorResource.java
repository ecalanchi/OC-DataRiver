package org.akaza.openclinica.web.restful;

import java.util.LinkedHashMap;
import java.util.Locale;

import org.akaza.openclinica.bean.odmbeans.OdmClinicalDataBean;
import org.akaza.openclinica.service.extract.GenerateClinicalDataService;

/**
 * 
 * @author jnyayapathi
 *
 */
public class ClinicalDataCollectorResource {
	private GenerateClinicalDataService generateClinicalDataService;
	
	//VG: 01/09/2017 - begin
//	public LinkedHashMap<String,OdmClinicalDataBean> generateClinicalData(String studyOID,String studySubjOID,String studyEventOID,String formVersionOID,boolean includeDNs,boolean includeAudits, Locale locale, int userId){
//		return getGenerateClinicalDataService().getClinicalData(studyOID, studySubjOID,studyEventOID,formVersionOID,includeDNs,includeAudits,locale, userId);
//	}
	public LinkedHashMap<String,OdmClinicalDataBean> generateClinicalData(String studyOID,String studySubjOID,String studyEventOID,String formVersionOID,boolean includeDNs,boolean includeAudits,boolean includeHiddenCrf, Locale locale, int userId){
		return getGenerateClinicalDataService().getClinicalData(studyOID, studySubjOID,studyEventOID,formVersionOID,includeDNs,includeAudits,includeHiddenCrf,locale, userId);
	}
	//VG: 01/09/2017 - end

	public GenerateClinicalDataService getGenerateClinicalDataService() {
		return generateClinicalDataService;
	}

	public void setGenerateClinicalDataService(
			GenerateClinicalDataService generateClinicalDataService) {
		this.generateClinicalDataService = generateClinicalDataService;
	}
}
