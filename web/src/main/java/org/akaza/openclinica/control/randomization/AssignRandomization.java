 package org.akaza.openclinica.control.randomization;
 
 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
 

 import org.akaza.openclinica.bean.admin.CRFBean;
import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.managestudy.StudyEventBean;
import org.akaza.openclinica.bean.managestudy.StudyGroupBean;
import org.akaza.openclinica.domain.datariver.RandomizationArmsBean;
import org.akaza.openclinica.domain.datariver.RandomizationCustomBean;
import org.akaza.openclinica.domain.datariver.RandomizationSubjectsBean;
import org.akaza.openclinica.domain.datariver.RandomizationVarBean;
import org.akaza.openclinica.bean.submit.EventCRFBean;
import org.akaza.openclinica.bean.submit.ItemDataBean;
import org.akaza.openclinica.bean.submit.SubjectGroupMapBean;
import org.akaza.openclinica.core.SessionManager;
import org.akaza.openclinica.dao.admin.CRFDAO;
import org.akaza.openclinica.dao.managestudy.StudyEventDAO;
import org.akaza.openclinica.dao.managestudy.StudyGroupDAO;
import org.akaza.openclinica.dao.hibernate.datariver.RandomizationArmsDao;
import org.akaza.openclinica.dao.hibernate.datariver.RandomizationCustomDao;
import org.akaza.openclinica.dao.hibernate.datariver.RandomizationSubjectsDao;
import org.akaza.openclinica.dao.hibernate.datariver.RandomizationVarDao;
import org.akaza.openclinica.dao.submit.CRFVersionDAO;
import org.akaza.openclinica.dao.submit.ItemDataDAO;
import org.akaza.openclinica.dao.submit.SubjectGroupMapDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 /**
  * @author DataRiver 19/mar/2012 added 28/11/2017
  *
  */
 public class AssignRandomization {
 	//Logger
     protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
 	
 	private SessionManager sm;
 	//Bean
     private EventCRFBean ecb = null;
     protected RandomizationVarBean rvb = null;
     protected RandomizationSubjectsBean rsb = null;
     protected SubjectGroupMapBean sgmb = null;
     //DAO
     protected StudyEventDAO sedao = null;
     private RandomizationVarDao randomizationVarDao;
     private RandomizationArmsDao randomizationArmsDao;
     private RandomizationSubjectsDao randomizationSubjectsDao;
     private RandomizationCustomDao randomizationCustomDao;
     protected SubjectGroupMapDAO sgmdao = null;
     protected CRFVersionDAO cvdao = null;
 
     protected String stratValues = null;
     protected ArrayList<Integer> subjectRandomizationArmsValue = null;
 
     
     
     
     
 	public ArrayList<Integer> getSubjectRandomizationArmsValue() {
		return subjectRandomizationArmsValue;
	}


	public AssignRandomization() {
 		super();
 	}
 
 
 	/**
 	 * @param sessionManager
 	 * @param ecb
 	 */
 	public AssignRandomization(SessionManager sessionManager, EventCRFBean eventCrfBean,RandomizationVarDao randomizationVarDao , RandomizationArmsDao randomizationArmsDao,  RandomizationSubjectsDao randomizationSubjectsDao ,RandomizationCustomDao randomizationCustomDao){
 		this.sm = sessionManager;
 		this.ecb = eventCrfBean;
 		sedao = new StudyEventDAO(sm.getDataSource());
 	    this.randomizationVarDao = randomizationVarDao;
 	    this.randomizationArmsDao = randomizationArmsDao;
 	    this.randomizationSubjectsDao =randomizationSubjectsDao;
 	    this.randomizationCustomDao =randomizationCustomDao;
         sgmdao = new SubjectGroupMapDAO(sm.getDataSource());
         cvdao = new CRFVersionDAO(sm.getDataSource());
         subjectRandomizationArmsValue = new ArrayList();
 
 	}
 	
 
 
 
 	/**
 	 * Get study and subject's values for randomization and assign a study group class ID (arm) to the current subject
 	 * @return allRight
 	 * @throws OpenClinicaException
 	 */
 	public boolean randomizeSubject() throws OpenClinicaException{
 		
 		Boolean randomized = false;
 		
 		//get a list of "randomization set" variables
 		ArrayList al = getCurrentRandomizationVarBean();
 		//System.out.println("[Luca]  getCurrentRandomizationVarBean.size = "+al.size());
 		Iterator iterator = al.iterator();
 		
 		/* 
 		 * If ArrayList is empty, subject should not be randomized in current EventCrf
 		 * ArrayList should not have more than one element, because a pair of CRF and StudyEvent 
 		 * could be associated to a single randomization event only. 
 		 */
 		while (iterator.hasNext()){
 			
 			//get first RandomizationVarBean			
 			rvb = (RandomizationVarBean) iterator.next();
 			
 			//Get current study subject's stratification values and randomization arm
 			Vector randomizableArms = getArmsForRandomization();
 			
 			//Get randomization list (if existing)
 			String randomListName = getCurrentRandomizationList();
 			//System.out.println("[Luca] randomizableArms: "+randomizableArms+" / randomListName: "+randomListName);
 			
 			Integer randomizedArm = 0;
 			Integer fileListRandomizationId = 0;
 			
 			if (randomListName != "")
 			{
 				//File List Randomization
 				try {
					RandomizationCustomBean fileListRandomization = getFileListRandomizationArm(randomListName);
					if (fileListRandomization != null){
						fileListRandomizationId = fileListRandomization.getId();
						randomizedArm = fileListRandomization.getRandomizzazione();
						System.out.println("arm");
						System.out.println(randomizedArm);
						
					}
				} catch (Exception e) {
					System.out.println("Subject has not been randomized due to an error in randomization file list");
					e.printStackTrace();
					return false;
				}
 				
 			} else if (randomListName == ""){ // WARNING: an error in file list randomization should never allow runtime randomization!					
 				
 				//Runtime Randomization
 				if (randomizableArms != null)
 					randomizedArm = getRandomizationArmValue(randomizableArms);
 
 			} 
 			
 			subjectRandomizationArmsValue.add(randomizedArm);
 			
 			
 			//stratValues != null is not required anymore: if study is randomized and randomized arm is returned, then I write it to database. 
 			//if ((!stratValues.isEmpty()) && (!randomizableArms.isEmpty()) && (randomizedArm.intValue() > 0)){
 			if (randomizedArm.intValue() > 0){
 				
 				//set randomization subject's values
 				rsb = new RandomizationSubjectsBean();
 				rsb.setStudySubjectId(getEcb().getStudySubjectId());
 				rsb.setArm(randomizedArm.intValue());				
 				rsb.setDateInserted(new Date());
 				rsb.setStratificationVarValues(stratValues);
 				rsb.setOwnerId(getSm().getUserBean().getId());
 				
 				//set study group map's values 
 				sgmb = new SubjectGroupMapBean();
 				sgmb.setStudyGroupClassId(rvb.getStudyGroupClassId());
 				sgmb.setStudySubjectId(getEcb().getStudySubjectId());
 				sgmb.setStudyGroupId(randomizedArm); //randomized arm
 				sgmb.setStatus(Status.AVAILABLE);
 				sgmb.setOwnerId(getSm().getUserBean().getId());
 				sgmb.setNotes("");
 				
 			    if ((sgmb.getStudyGroupId() > 0) && (rsb.getStudySubjectId() > 0)) {
 			    	//insert
 					getRandomizationSubjectsDao().create(rsb);
 			        sgmdao.create(sgmb);
 			        randomized = true;
 			    }
 			    
 			    //File List Randomization update ("randomizzazione" database) 
 				if (randomListName != "" && randomized == true && fileListRandomizationId.intValue() > 0)
 				{					
 
 					getRandomizationCustomDao().updateRandomizationList(randomListName,getEcb().getStudySubjectId(),getSm().getUserBean().getId(),fileListRandomizationId);
 					
 				}
 		    }
 			
 		} //end while
 		System.out.println("rand "+randomized);
 		return randomized;
 	}
 
 
 	/**
 	 * @param randomListName
 	 * @return 
 	 */
 	private RandomizationCustomBean getFileListRandomizationArm(String randomListName) {
 		
 		Integer[] randomizationArm = null;
 		Connection randomConn = null;
 		PreparedStatement ps = null;
 		String findRandomizationListArm = null;
 		ResultSet rs = null;
 		RandomizationCustomBean randCustomBean= null;
 		logger.info("File list randomization: opening connection...");
 		//Are there stratification vars?
 		String listStratvalues = stratValues.replace(',', '-');
 		if (rvb.getStratificationVarIds().length() > 0) {
 			randCustomBean =randomizationCustomDao.findRandomizationListArmWithStrato(randomListName, listStratvalues);
 		}else{
 			randCustomBean =randomizationCustomDao.findRandomizationListArm(randomListName);
 		}
 		
 		return randCustomBean;
 		
 		
 	}
 
 
 	/**
 	 * Verify if stratValues is not empty
 	 * @return
 	 */
 	private boolean isStratValuesNotEmpty() {
 		if (stratValues == null || stratValues.isEmpty())
 			return false;
 		
 		String token = "";
 		StringTokenizer st = new StringTokenizer(stratValues,",");
 		while(st.hasMoreTokens()) {
 			token += st.nextToken().toString().trim();
 		}
 		if (token.length() > 0)
 			return true;
 		else
 			return false;
 	}
 
 
 
 	/**
 	 * Get randomization arm
 	 * @param randomizableArms
 	 * @return
 	 */
 	public Integer getRandomizationArmValue(Vector randomizableArms) {
 		//randomize subject
 		if(!randomizableArms.isEmpty()){
 			Random rand = new Random();
 			//get random row from randomizableArm vector
 			int arm = (Integer) randomizableArms.get(rand.nextInt(randomizableArms.size()));
 			return arm;
 		} else {
 			return 0;
 		}
 	}
 
 
 	/**
 	 * Check which arms are available for current subject (based on stratValues)
 	 * @return
 	 */
 	public Vector getArmsForRandomization() {
 		
 		Boolean isClusterFull = true;
 		Boolean hasCluster = true; //randomization with clusters and arm ratio: if a single arm has not a ratio value, turn this flag to false
 		Boolean hasStratVars = false; //randomization with stratification variables
 		Vector allowed = new Vector();	
 		Vector allArms = new Vector();
 				
 		//Check if subject has been randomized
 		if(!hasBeenRandomized(rvb.getStudyGroupClassId(), getEcb().getStudySubjectId())){
 			
 			//Are there stratification vars?
 			if (rvb.getStratificationVarIds().length() > 0) {
 				hasStratVars = true;
 				//get stratification values for current subject
 				stratValues = getStudySubjectStratificationValues();
 			}
 		
 			//Get arms for current randomization set (study_group_id in study_group_class_id)
 			ArrayList armsList = getRandomizationArmsDao().findAllByStudyGroupClassId(rvb.getStudyGroupClassId());
 			
 			//Get subject's randomization cluster progress
 			for (int i = 0; i<armsList.size(); i++){				
 				RandomizationArmsBean rab = (RandomizationArmsBean) armsList.get(i);
 				if (rab.getRatio() == null){
 					//if ratio is less or equals zero, then randomization has no clusters
 					hasCluster = false;
 				}
 				
 				int countSubjects = 0;
 				if (hasStratVars){
 					if (isStratValuesNotEmpty()){
 						//For each arm, get count of subjects and ratio
 						countSubjects = getRandomizationSubjectsDao().getCountOfSubjectsBasedOnArmAndStratificationVarValues(rab.getArm(), stratValues).intValue();
 					} else {
 						//randomization has stratification variables, but current patient hasn't any value and should not be randomized
 						return null;
 					} 
 				} else { 
 					//Randomization has not stratification values
 					countSubjects = getRandomizationSubjectsDao().getCountOfSubjectsPerArm(rab.getArm()).intValue();
 				}
 				
 				Vector currentArm = new Vector();
 				currentArm.add(rab.getArm());// (0) arm id
 				currentArm.add(countSubjects);// (1) count
 				currentArm.add(rab.getRatio());// (2) ratio	
 				currentArm.add(0);// (3) check value for cluster count (initially set to 0)
 				
 				allArms.add(currentArm);
 			}
 
 			if (!hasCluster){
 				//randomization without clusters: all arms are available
 				return allArms;
 			}
 			
 			while (isClusterFull){ //check if all arms are greater than their own ratio value
 				for(int i=0; i<allArms.size(); i++){
 					Vector currentArm = new Vector();
 					currentArm = (Vector) allArms.get(i);
 					currentArm.set(3, (((Integer) currentArm.get(1)).intValue() - ((Integer) currentArm.get(2)).intValue()));// put ("count of subject" - "ratio") in currentArm(3) 
 					if (((Integer) currentArm.get(3)).intValue() < 0){ //set flag to false if subjects in current arm are less than arm's ratio value
 						isClusterFull = false;
 					}					
 				}
 				
 				if (isClusterFull){ //if previous check was ok, subtract ratio value from each arm (getting rid of a cluster)
 					for (int j=0; j<allArms.size(); j++){
 						Vector currentArm = new Vector();
 						currentArm = (Vector) allArms.get(j);
 						currentArm.set(1, ((Integer) currentArm.get(3)).intValue());
 					}
 				} else { //we're in current cluster: check which arms can be used in current randomization
 					for (int k=0; k<allArms.size(); k++){
 						Vector currentArm = new Vector();
 						currentArm = (Vector) allArms.get(k);
 						if ((((Integer) currentArm.get(2)).intValue() - ((Integer) currentArm.get(1)).intValue()) > 0){
 							allowed.add(((Integer) currentArm.get(0)).intValue()); //these arms can be used
 						}
 					}
 					return allowed;
 				} 
 			}
 		} return null; //subject has been previously randomized: return null
 	}
 
 
 	/**
 	 * Get current study subject's stratification values
 	 * @return
 	 */
 	private String getStudySubjectStratificationValues() {
 		//Get stratification variables for current randomization set (study_group_class_id)
 		StringTokenizer stratVar = new StringTokenizer(rvb.getStratificationVarIds(),",");
 		
 		//Get subject's stratification values
 		ItemDataDAO idd = new ItemDataDAO(getSm().getDataSource());
 		StringBuffer stratValues = new StringBuffer();
 		while(stratVar.hasMoreTokens()) {
 			int item = Integer.parseInt(stratVar.nextToken());
 			ItemDataBean idb = idd.findByItemIdAndEventCRFId(item, this.getEcb().getId());
 			stratValues.append(idb.getValue() + ",");
 		}
 		if (stratValues.length() > 1){ //there's a comma at least 
 			return stratValues.substring(0, stratValues.length() - 1); //remove last comma
 		} else {
 			return null;
 		}
 	}
 
 
 	/**
 	 * Is subject in randomization_subjects table with current study_group_class_id?
 	 * @param studyGroupClassID
 	 * @param studySubjectId
 	 * @return
 	 */
 	public boolean hasBeenRandomized(int studyGroupClassID, int studySubjectId){
         
 		if((studyGroupClassID <= 0) || (studySubjectId <= 0)){
 			//study is randomized, but randomization parameters haven't been set:
 			//subject cannot be randomized, return true
 			return true;
 		} else {
 			//Check if subject is in randomized_subject for selected randomization set (study group class id)
 			RandomizationSubjectsBean randomizedSubject =  getRandomizationSubjectsDao().findByGroupClassIdAndSubjectId(studyGroupClassID, studySubjectId);
 			if (randomizedSubject == null){
 				//subject has not been randomized yet: return false
 				return false;
 			} else {
 				//subject has been randomized: return true
 				return true;
 			}
 		}
 
 	}
 
 
 	/**
 	 * Get current RandomizationVarBean
 	 * @return
 	 */
 	private ArrayList getCurrentRandomizationVarBean() {
 		//Get Study Event Definition ID
         StudyEventBean seb = (StudyEventBean) sedao.findByPK(getEcb().getStudyEventId());
         int studyEventDefinitionID = seb.getStudyEventDefinitionId();
 		
         //Get CRF Id
         CRFDAO crfDAO = new CRFDAO(getSm().getDataSource());
         CRFBean crfBean = crfDAO.findByVersionId(getEcb().getCRFVersionId());
         int crfID = crfBean.getId();
//         System.out.println("[Luca] StudyEventBean "+seb+" getEcb:"+getEcb()); 
//         System.out.println("[Luca] calling randomizationVarDao.findAllByStudyEventDefinitionIdAndCrfId("+studyEventDefinitionID+", "+crfID+")...");
         
 		//Get study_group_class_id by study_event_definition_id and crf_id from randomization_var table
         ArrayList al = randomizationVarDao.findAllByStudyEventDefinitionIdAndCrfId(studyEventDefinitionID, crfID);
         
 //        System.out.println(al.get(0));
         
 		return al;
 	}
 	
 	public ArrayList getRandomizationArmsName(){
 		ArrayList al = new ArrayList();
 		StudyGroupDAO sgd = new StudyGroupDAO(getSm().getDataSource());
 		Iterator i = subjectRandomizationArmsValue.iterator();
 		while(i.hasNext()){
 			int arm = Integer.parseInt(i.next().toString());
 			StudyGroupBean sgb = (StudyGroupBean) sgd.findByPK(arm);
 			al.add(sgb.getName());
 		}		
 		return al;
 	}
 
 	/**
 	 * Get randomizationListName of current randomization. If randomization is automatic, randomizationListName will be empty
 	 * @return randomizationListName
 	 */
 	private String getCurrentRandomizationList(){
 		String randomizationListName = rvb.getRandomizationList();
 		if (randomizationListName != null)
 			return randomizationListName;	
 		else
 			return "";
 	}
 
 
 	public RandomizationVarDao getRandomizationVarDao() {
 		return randomizationVarDao;
 	}
 
 
 	public void setRandomizationVarDao(RandomizationVarDao randomizationVarDao) {
 		this.randomizationVarDao = randomizationVarDao;
 	}
 
 
 	public RandomizationArmsDao getRandomizationArmsDao() {
 		return randomizationArmsDao;
 	}
 
 
 	public void setRandomizationArmsDao(RandomizationArmsDao randomizationArmsDao) {
 		this.randomizationArmsDao = randomizationArmsDao;
 	}
 
 
 	public RandomizationSubjectsDao getRandomizationSubjectsDao() {
 		return randomizationSubjectsDao;
 	}
 
 
 	public void setRandomizationSubjectsDao(RandomizationSubjectsDao randomizationSubjectsDao) {
 		this.randomizationSubjectsDao = randomizationSubjectsDao;
 	}
 
 
 	public RandomizationCustomDao getRandomizationCustomDao() {
 		return randomizationCustomDao;
 	}
 
 
 	public void setRandomizationCustomDao(RandomizationCustomDao randomizationCustomDao) {
 		this.randomizationCustomDao = randomizationCustomDao;
 	}
 
 
 	public SessionManager getSm() {
 		return sm;
 	}
 
 
 	public void setSm(SessionManager sm) {
 		this.sm = sm;
 		sedao = new StudyEventDAO(getSm().getDataSource());
         sgmdao = new SubjectGroupMapDAO(getSm().getDataSource());
         cvdao = new CRFVersionDAO(getSm().getDataSource());
         subjectRandomizationArmsValue = new ArrayList();
 	}
 
 
 	public EventCRFBean getEcb() {
 		return ecb;
 	}
 
 
 	public void setEcb(EventCRFBean ecb) {
 		this.ecb = ecb;
 	}
 }
 
