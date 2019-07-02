/*
 * OpenClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: http://www.openclinica.org/license
 * copyright 2003-2005 Akaza Research
 */
package org.akaza.openclinica.control.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.akaza.openclinica.bean.login.UserAccountBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.control.SpringServletAccess;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.control.form.Validator;
import org.akaza.openclinica.dao.hibernate.ConfigurationDao;
import org.akaza.openclinica.dao.hibernate.PasswordRequirementsDao;
import org.akaza.openclinica.dao.login.UserAccountDAO;
import org.akaza.openclinica.dao.managestudy.StudyDAO;
import org.akaza.openclinica.domain.datariver.DatariverEmailBean;
import org.akaza.openclinica.i18n.core.LocaleResolver;
import org.akaza.openclinica.i18n.util.ResourceBundleProvider;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;
import org.apache.commons.lang.StringUtils;
/**
 * @author jxu
 * @version CVS: $Id: UpdateProfileServlet.java,v 1.9 2005/02/23 18:58:11 jxu
 *          Exp $
 *
 * Servlet for processing 'update profile' request from user
 */
public class UpdateProfileServlet extends SecureController {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2519124535258437372L;

	@Override
    public void mayProceed() throws InsufficientPermissionException {

    }

    @Override
    public void processRequest() throws Exception {

        String action = request.getParameter("action");// action sent by user
        StudyDAO sdao = new StudyDAO(sm.getDataSource());
        UserAccountDAO udao = new UserAccountDAO(sm.getDataSource());
        UserAccountBean userBean1 = (UserAccountBean) udao.findByUserName(ub.getName());

        Collection studies = sdao.findAllByUser(ub.getName());
        
    	//+DR added by DataRiver (EC) 27/03/2018 
        if (request.getParameter("SendTestEmail") != null) {
        	Boolean messageSent = sendNewTestEmail();        	
        	addPageMessage(messageSent ? respage.getString("your_message_sent_succesfully") : respage.getString("mail_cannot_be_sent_to_admin"));
        	forwardPage(Page.UPDATE_PROFILE);
        } else {
        //+DR end added by DataRiver (EC) 27/03/2018         

        if (StringUtils.isBlank(action)) {
            request.setAttribute("studies", studies);
            session.setAttribute("userBean1", userBean1);
            forwardPage(Page.UPDATE_PROFILE);
        } else {
            if ("confirm".equalsIgnoreCase(action)) {
                logger.info("confirm");
                request.setAttribute("studies", studies);
                confirmProfile(userBean1, udao);

            } else if ("submit".equalsIgnoreCase(action)) {
                logger.info("submit");
                submitProfile(udao);

                addPageMessage(respage.getString("profile_updated_succesfully"));
                ub.incNumVisitsToMainMenu();
                forwardPage(Page.MENU_SERVLET);
            }
        }
        
        }//+DR end added by DataRiver (EC) 27/03/2018

    }

	private void confirmProfile(UserAccountBean userBean1, UserAccountDAO udao) throws Exception {
        Validator v = new Validator(request);
        FormProcessor fp = new FormProcessor(request);

        v.addValidation("firstName", Validator.NO_BLANKS);
        v.addValidation("lastName", Validator.NO_BLANKS);
        v.addValidation("email", Validator.IS_A_EMAIL);
        if (!userBean1.isLdapUser()) {
            v.addValidation("passwdChallengeQuestion", Validator.NO_BLANKS);
            v.addValidation("passwdChallengeAnswer", Validator.NO_BLANKS);
            v.addValidation("oldPasswd", Validator.NO_BLANKS);// old password
        String password = fp.getString("passwd").trim();

        ConfigurationDao configurationDao = SpringServletAccess
                .getApplicationContext(context)
                .getBean(ConfigurationDao.class);

        org.akaza.openclinica.core.SecurityManager sm =
                (org.akaza.openclinica.core.SecurityManager) SpringServletAccess
                .getApplicationContext(context)
                .getBean("securityManager");

        String newDigestPass = sm.encrytPassword(password, getUserDetails());
        List<String> pwdErrors = new ArrayList<String>();

        if (!StringUtils.isBlank(password)) {
            v.addValidation("passwd", Validator.IS_A_PASSWORD);// new password
            v.addValidation("passwd1", Validator.CHECK_SAME, "passwd");// confirm
            // password

            PasswordRequirementsDao passwordRequirementsDao = new PasswordRequirementsDao(configurationDao);
            Locale locale = LocaleResolver.getLocale(request);
            ResourceBundle resexception = ResourceBundleProvider.getExceptionsBundle(locale);

            pwdErrors = PasswordValidator.validatePassword(
                            passwordRequirementsDao,
                            udao,
                            userBean1.getId(),
                            password,
                            newDigestPass,
                            resexception);
        }
        v.addValidation("phone", Validator.NO_BLANKS);
        errors = v.validate();
        for (String err: pwdErrors) {
            v.addError(errors, "passwd", err);
        }

        userBean1.setFirstName(fp.getString("firstName"));
        userBean1.setLastName(fp.getString("lastName"));
        userBean1.setEmail(fp.getString("email"));

        //+DR end modified by DataRiver (EC) 01/07/2019
        //prevent specialist user to change his Institutional
        //userBean1.setInstitutionalAffiliation(fp.getString("institutionalAffiliation"));
        if (!userBean1.getInstitutionalAffiliation().toLowerCase().contains("[lab]")) userBean1.setInstitutionalAffiliation(fp.getString("institutionalAffiliation"));
        //+DR end modified by DataRiver (EC) 01/07/2019

        userBean1.setPasswdChallengeQuestion(fp.getString("passwdChallengeQuestion"));
        userBean1.setPasswdChallengeAnswer(fp.getString("passwdChallengeAnswer"));
        userBean1.setPhone(fp.getString("phone"));
        userBean1.setActiveStudyId(fp.getInt("activeStudyId"));
        StudyDAO sdao = new StudyDAO(this.sm.getDataSource());

        StudyBean newActiveStudy = (StudyBean) sdao.findByPK(userBean1.getActiveStudyId());
        request.setAttribute("newActiveStudy", newActiveStudy);

        if (errors.isEmpty()) {
            logger.info("no errors");

            session.setAttribute("userBean1", userBean1);
            String oldPass = fp.getString("oldPasswd").trim();

            if (!userBean1.isLdapUser() && !sm.isPasswordValid(ub.getPasswd(), oldPass, getUserDetails())) {
                Validator.addError(errors, "oldPasswd", resexception.getString("wrong_old_password"));
                request.setAttribute("formMessages", errors);
                // addPageMessage("Wrong old password. Please try again.");
                forwardPage(Page.UPDATE_PROFILE);
            } else {
                if (!StringUtils.isBlank(fp.getString("passwd"))) {
                    userBean1.setPasswd(newDigestPass);
                    userBean1.setPasswdTimestamp(new Date());
                }
                session.setAttribute("userBean1", userBean1);
                forwardPage(Page.UPDATE_PROFILE_CONFIRM);
            }

        } else {
            logger.info("has validation errors");
            session.setAttribute("userBean1", userBean1);
            request.setAttribute("formMessages", errors);
            forwardPage(Page.UPDATE_PROFILE);
        }

    }
	}

    /**
     * Updates user new profile
     *
     */
    private void submitProfile(UserAccountDAO udao) {
        logger.info("user bean to be updated:" + ub.getId() + ub.getFirstName());

        UserAccountBean userBean1 = (UserAccountBean) session.getAttribute("userBean1");
        if (userBean1 != null) {
        	userBean1.setLastVisitDate(new Date());
            userBean1.setUpdater(ub);
            udao.update(userBean1);

        	session.setAttribute("userBean", userBean1);
            ub = userBean1;
            session.removeAttribute("userBean1");
        }
    }
    
	/**
	 * Send a test email (type_id=5)
	 * 
	 * @author DataRiver (EC) 27/03/2018
	 * @return
	 */
	private Boolean sendNewTestEmail() {
		Boolean messageSent = false;
		DatariverEmailBean datariverEmail = getDatariverEmailDao().getDatariverEmailTest();
	    
		if (datariverEmail != null){
	    	String email_subject = datariverEmail.getSubject();
	    	String recipients = ub.getEmail() == "" ? datariverEmail.getRecipients() : ub.getEmail() + ", " + datariverEmail.getRecipients();
	    	String bcc = datariverEmail.getBcc();
	    	String body = datariverEmail.getHtmlBody();
	    	String sender = datariverEmail.getSender();
		
	    	messageSent = sendBackgroundEmail(recipients, bcc, sender, email_subject, body, true, datariverEmail.getAttachmentPath());	    	
			
	    	//log email with filename [TEST]_[send_email_test]_yyyy-MM-dd_HHmmssS.html
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
	    		"TEST");		                                	
		}
		
        return messageSent;
		
	}        

}
