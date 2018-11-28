/*
 * OpenClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: http://www.openclinica.org/license
 * copyright 2003-2005 Akaza Research
 */
package org.akaza.openclinica.control.admin;

import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.bean.managestudy.StudyEventDefinitionBean;
import org.akaza.openclinica.control.SpringServletAccess;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.control.submit.SubmitDataServlet;
import org.akaza.openclinica.dao.core.CoreResources;
import org.akaza.openclinica.dao.hibernate.datariver.DatariverEmailDao;
import org.akaza.openclinica.dao.hibernate.datariver.DatariverEnrollmentEnableDao;
import org.akaza.openclinica.dao.hibernate.datariver.DatariverEnrollmentEnableLogDao;
import org.akaza.openclinica.dao.login.UserAccountDAO;
import org.akaza.openclinica.dao.managestudy.EventDefinitionCRFDAO;
import org.akaza.openclinica.dao.managestudy.StudyDAO;
import org.akaza.openclinica.dao.managestudy.StudyEventDefinitionDAO;
import org.akaza.openclinica.dao.managestudy.StudySubjectDAO;
import org.akaza.openclinica.dao.service.StudyConfigService;
import org.akaza.openclinica.dao.service.StudyParameterValueDAO;
import org.akaza.openclinica.domain.datariver.DatariverEmailBean;
import org.akaza.openclinica.domain.datariver.DatariverEnrollmentEnableBean;
import org.akaza.openclinica.domain.datariver.DatariverEnrollmentEnableLogBean;
import org.akaza.openclinica.service.pmanage.ParticipantPortalRegistrar;
import org.akaza.openclinica.service.pmanage.RandomizationRegistrar;
import org.akaza.openclinica.service.pmanage.SeRandomizationDTO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author jxu
 *
 * Processes the reuqest of 'view study details'
 */
public class ViewStudyServlet extends SecureController {
	
	private DatariverEnrollmentEnableDao datariverEnrollmentEnableDAO;
	private DatariverEnrollmentEnableLogDao datariverEnrollmentEnableLogDAO;
	private DatariverEmailDao datariverEmailDao;
	
    /**
     * Checks whether the user has the correct privilege
     */
    @Override
    public void mayProceed() throws InsufficientPermissionException {
        if (ub.isSysAdmin()) {
            return;
        }

        if (SubmitDataServlet.mayViewData(ub, currentRole)) {
            return;
        }

        addPageMessage(respage.getString("no_have_correct_privilege_current_study") + respage.getString("change_study_contact_sysadmin"));
        throw new InsufficientPermissionException(Page.MENU_SERVLET, resexception.getString("not_admin"), "1");

    }
    
	/**
     *  +DR added by DataRiver Fabio Benedetti 23/06/2014 [Enrico Calanchi 06/11/2018]
     *  Adding information about enrollment enabling in sites
     */
	public DatariverEnrollmentEnableDao getDatariverEnrollmentEnableDAO() {
		datariverEnrollmentEnableDAO = this.datariverEnrollmentEnableDAO != null ? datariverEnrollmentEnableDAO : (DatariverEnrollmentEnableDao) SpringServletAccess.getApplicationContext(context).getBean("datariverEnrollmentEnableDAO");
        return datariverEnrollmentEnableDAO;
	}

	public DatariverEnrollmentEnableLogDao getDatariverEnrollmentEnableLogDAO() {
		datariverEnrollmentEnableLogDAO = this.datariverEnrollmentEnableLogDAO != null ? datariverEnrollmentEnableLogDAO : (DatariverEnrollmentEnableLogDao) SpringServletAccess.getApplicationContext(context).getBean("datariverEnrollmentEnableLogDAO");
        return datariverEnrollmentEnableLogDAO;
	}
	
	public DatariverEmailDao getDatariverEmailDao() {
		datariverEmailDao = this.datariverEmailDao != null ? datariverEmailDao : (DatariverEmailDao) SpringServletAccess.getApplicationContext(context).getBean("datariverEmailDao");
        return datariverEmailDao;
	}

    @Override
    public void processRequest() throws Exception {

        StudyDAO sdao = new StudyDAO(sm.getDataSource());
        FormProcessor fp = new FormProcessor(request);
        
        int studyId = fp.getInt("id");
        if (studyId == 0) {
            addPageMessage(respage.getString("please_choose_a_study_to_view"));
            forwardPage(Page.STUDY_LIST_SERVLET);
        } else {
            if (currentStudy.getId() != studyId && currentStudy.getParentStudyId() != studyId) {
                checkRoleByUserAndStudy(ub, studyId, 0);
            }
            
            /**
             *  +DR added by DataRiver Fabio Benedetti 23/06/2014 [Enrico Calanchi 06/11/2018]
             *  Adding information about enrollment enabling in sites
             */
            //security checks
            if (fp.getRequest().getMethod().contains("POST")){
            	DatariverEnrollmentEnableLogBean log = new DatariverEnrollmentEnableLogBean(new Date(),ub.getId() );
            	StudyBean sb;
            	if (fp.getString("submit").equals("Disable")||fp.getString("submit").equals("Enable")){	
            		int idToChange=fp.getInt("enrChangeId");
            		sb = (StudyBean) sdao.findByPK(idToChange);
            		boolean currEn=Boolean.parseBoolean(fp.getString("enrChangeBool"));
            		log.setStudyId(idToChange);
            		log.setEnrollmentEnabledNewStatus(!currEn);
            		getDatariverEnrollmentEnableDAO().changeEnable(idToChange,!currEn);
            		this.sendDatariverEmailEnrollment(currEn==true?1:2, studyId, sb.getParentStudyId() > 0 ? sdao.findByPK(sb.getParentStudyId()).getName() + ", " + sb.getAbbreviatedName() : sb.getAbbreviatedName());
            	} else if (fp.getString("submit").contains("Enable All")){
            		int idParentToChange=fp.getInt("studyToChange");
            		sb = (StudyBean) sdao.findByPK(idParentToChange);
            		log.setStudyId(idParentToChange);
            		log.setEnrollmentEnabledNewStatus(true);
            		getDatariverEnrollmentEnableDAO().updateChildOfAStudy(studyId,true);
            		this.sendDatariverEmailEnrollment(2, studyId, sb.getParentStudyId() > 0 ? sdao.findByPK(sb.getParentStudyId()).getName() + ", " + sb.getAbbreviatedName() : sb.getAbbreviatedName());
            	} else if (fp.getString("submit").contains("Disable All")){
            		int idParentToChange=fp.getInt("studyToChange");
            		sb = (StudyBean) sdao.findByPK(idParentToChange);
            		log.setStudyId(idParentToChange);
            		log.setEnrollmentEnabledNewStatus(false);
            		getDatariverEnrollmentEnableDAO().updateChildOfAStudy(studyId,false);
            		this.sendDatariverEmailEnrollment(1, studyId, sb.getParentStudyId() > 0 ? sdao.findByPK(sb.getParentStudyId()).getName() + ", " + sb.getAbbreviatedName() : sb.getAbbreviatedName());
            	}
            	getDatariverEnrollmentEnableLogDAO().insertLog(log);
            }

            String viewFullRecords = fp.getString("viewFull");
            StudyBean study = (StudyBean) sdao.findByPK(studyId);


            StudyConfigService scs = new StudyConfigService(sm.getDataSource());
            study = scs.setParametersForStudy(study);

            StudyParameterValueDAO spvdao = new StudyParameterValueDAO(sm.getDataSource());
            
            //+DR modified by DataRiver (EC) 08/11/2018
            //commented to prevent conflicts with DR randomization feature
//            String randomizationStatusInOC = spvdao.findByHandleAndStudy(study.getId(), "randomization").getValue();
//            String participantStatusInOC = spvdao.findByHandleAndStudy(study.getId(), "participantPortal").getValue();
//            if(participantStatusInOC=="") participantStatusInOC="disabled";
//            if(randomizationStatusInOC=="") randomizationStatusInOC="disabled";
//
//            RandomizationRegistrar randomizationRegistrar = new RandomizationRegistrar();
//            SeRandomizationDTO seRandomizationDTO = randomizationRegistrar.getCachedRandomizationDTOObject(study.getOid(), false);
//
//            if (seRandomizationDTO!=null && seRandomizationDTO.getStatus().equalsIgnoreCase("ACTIVE") && randomizationStatusInOC.equalsIgnoreCase("enabled")){
//                study.getStudyParameterConfig().setRandomization("enabled");
//            }else{
//                study.getStudyParameterConfig().setRandomization("disabled");
//             };
//
//
//             ParticipantPortalRegistrar  participantPortalRegistrar = new ParticipantPortalRegistrar();
//             String pStatus = participantPortalRegistrar.getCachedRegistrationStatus(study.getOid(), session);
//             if (participantPortalRegistrar!=null && pStatus.equalsIgnoreCase("ACTIVE") && participantStatusInOC.equalsIgnoreCase("enabled")){
//                 study.getStudyParameterConfig().setParticipantPortal("enabled");
//             }else{
//                 study.getStudyParameterConfig().setParticipantPortal("disabled");
//              };            
            //+DR end modified by DataRiver (EC) 08/11/2018


            request.setAttribute("studyToView", study);
            if ("yes".equalsIgnoreCase(viewFullRecords)) {
                UserAccountDAO udao = new UserAccountDAO(sm.getDataSource());
                StudySubjectDAO ssdao = new StudySubjectDAO(sm.getDataSource());
                ArrayList sites = new ArrayList();
                ArrayList userRoles = new ArrayList();
                ArrayList subjects = new ArrayList();
                if (this.currentStudy.getParentStudyId() > 0 && this.currentRole.getRole().getId() > 3) {
                    sites.add(this.currentStudy);
                    userRoles = udao.findAllUsersByStudy(currentStudy.getId());
                    subjects = ssdao.findAllByStudy(currentStudy);
                } else {
                    sites = (ArrayList) sdao.findAllByParent(studyId);
                    userRoles = udao.findAllUsersByStudy(studyId);
                    subjects = ssdao.findAllByStudy(study);
                }

                // find all subjects in the study, include ones in sites
                StudyEventDefinitionDAO seddao = new StudyEventDefinitionDAO(sm.getDataSource());
                EventDefinitionCRFDAO edcdao = new EventDefinitionCRFDAO(sm.getDataSource());
                // StudyEventDAO sedao = new StudyEventDAO(sm.getDataSource());

//                ArrayList displayStudySubs = new ArrayList();
//                for (int i = 0; i < subjects.size(); i++) {
//                    StudySubjectBean studySub = (StudySubjectBean) subjects.get(i);
//                    // find all events
//                    ArrayList events = sedao.findAllByStudySubject(studySub);
//
//                    // find all eventcrfs for each event
//                    EventCRFDAO ecdao = new EventCRFDAO(sm.getDataSource());
//
//                    DisplayStudySubjectBean dssb = new DisplayStudySubjectBean();
//                    dssb.setStudyEvents(events);
//                    dssb.setStudySubject(studySub);
//                    displayStudySubs.add(dssb);
//                }

                // find all events in the study, include ones in sites
                ArrayList definitions = seddao.findAllByStudy(study);

                for (int i = 0; i < definitions.size(); i++) {
                    StudyEventDefinitionBean def = (StudyEventDefinitionBean) definitions.get(i);
                    ArrayList crfs = (ArrayList) edcdao.findAllActiveParentsByEventDefinitionId(def.getId());
                    def.setCrfNum(crfs.size());

                }
                String moduleManager = CoreResources.getField("moduleManager");
                request.setAttribute("moduleManager", moduleManager);

                String portalURL = CoreResources.getField("portalURL");
                request.setAttribute("portalURL", portalURL);

                request.setAttribute("config", study);
                
                /**
                 *  +DR added by DataRiver Fabio Benedetti 23/06/2014 [Enrico Calanchi 06/11/2018]
                 *  Adding information about enrollment enabling in sites
                 */
                HashMap<Integer,Boolean> enroEn=this.createEnableHash(getDatariverEnrollmentEnableDAO().findAll());
                sites=addEnrollingEnable(sites,enroEn);

                request.setAttribute("studyId", studyId);

                request.setAttribute("sitesToView", sites);
                request.setAttribute("siteNum", sites.size() + "");

                request.setAttribute("userRolesToView", userRoles);
                request.setAttribute("userNum", userRoles.size() + "");

                // request.setAttribute("subjectsToView", displayStudySubs);
                // request.setAttribute("subjectNum", subjects.size() + "");

                request.setAttribute("definitionsToView", definitions);
                request.setAttribute("defNum", definitions.size() + "");
                forwardPage(Page.VIEW_FULL_STUDY);

            } else {
                forwardPage(Page.VIEW_STUDY);
            }
        }
    }

    @Override
    protected String getAdminServlet() {
        if (ub.isSysAdmin()) {
            return SecureController.ADMIN_SERVLET_CODE;
        } else {
            return "";
        }
    }
    
    /**
     *  +DR added by DataRiver Fabio Benedetti 22/06/2014 [Enrico Calanchi 06/11/2018]
     *  Enabling enrollment 
     */
    private HashMap<Integer,Boolean> createEnableHash(ArrayList<DatariverEnrollmentEnableBean> findAll){
    	HashMap<Integer,Boolean> en=new HashMap();
    	for(DatariverEnrollmentEnableBean current : findAll){
    		en.put(current.getStudyId(),new Boolean(current.isEnable()));
    	}
    	return en;
    }

    private ArrayList addEnrollingEnable(ArrayList sit, HashMap<Integer,Boolean> enEn) {
    	ArrayList tmp=new ArrayList();
    	for(int i = 0; i < sit.size(); i++){
	    	StudyBean current=(StudyBean) sit.get(i);
	    	current.setEnrollmentEn(enEn.get(current.getId()));
	    	tmp.add(current);
    	}
    	return tmp;
    }
    
    /**
     * +DR added by DataRiver - Fabio Benedetti 26/06/2014, Enrico Calanchi 06/11/2018
     * 
	 * Sent an email when a Enrollment is enable or disable
	 * type 1 -> disable
	 * type 2 -> enable
	 */
	public Boolean sendDatariverEmailEnrollment(int type, int studyId, String protocol) {
		
		Boolean messageSent = false;
		DatariverEmailBean datariverEmail = getDatariverEmailDao().getDatariverEmailEnrollment(studyId, type);
	    
		if (datariverEmail != null){
	    	String email_subject = datariverEmail.getSubject();
	    	String recipients = ub.getEmail() == "" ? datariverEmail.getRecipients() : ub.getEmail() + ", " + datariverEmail.getRecipients();
	    	String bcc = datariverEmail.getBcc();
	    	String body = datariverEmail.getHtmlBody();
	    	String sender = datariverEmail.getSender();
	    	
	    	//email parameters
	    	email_subject = email_subject.replaceAll("\\{protocol\\}", protocol);
	    	email_subject = email_subject.replaceAll("\\{user\\}", ub.getName());	    	
	    		
	    	body = body.replaceAll("\\{protocol\\}", protocol);
	    	body = body.replaceAll("\\{user\\}", ub.getName());
	    	
	//	    	System.out.println("subject: " + email_subject);
	//		    System.out.println("body: " + body);
		
	    	messageSent = sendBackgroundEmail(recipients, bcc, sender, email_subject, body, true, datariverEmail.getAttachmentPath());		
			
	    	//log email with filename [STUDYNAME]_[enrollment_disable|enrollment_enable]_yyyy-MM-dd_HHmmssS.html
	    	logDatariverEmail(
	    		messageSent ? "sent" : "failed", 
	    		body, 
	    		datariverEmail.getEmailId(), 
	    		datariverEmail.getEmailTypeId(), 
	    		email_subject, 
	    		recipients, 
	    		sender, 
	    		bcc, 
	    		ub, 
	    		currentStudy.getAbbreviatedName());		                                	
		}
		
        return messageSent;
	}

}
