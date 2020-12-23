package org.akaza.openclinica.dao.hibernate.datariver;
 
import java.util.HashMap;
import java.util.List;

import org.akaza.openclinica.domain.datariver.LotteryCustomBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * @author DataRiver (EC) 21/12/2020
 *
 */
 public class LotteryCustomDao {
 	
 private SessionFactory sessionFactory;
 	
/**
 * @return the sessionFactory
 */
 public SessionFactory getSessionFactory() {
     return sessionFactory;
 }
 
 /**
  * @param sessionFactory
  *            the sessionFactory to set
  */
 public void setSessionFactory(SessionFactory sessionFactory) {
     this.sessionFactory = sessionFactory;
 }
 
 /**
  * @return Session Object
  */
 protected Session getCurrentSession() {
     return sessionFactory.getCurrentSession();
 }
 
 @SuppressWarnings("unchecked")
 @Transactional
 public HashMap<String, Integer> getOutcomeListBySite(Integer siteId){
//	System.out.println("----- getOutcomeListBySite");
 	String query="SELECT 'L' as outcome, COUNT(*) FROM lottery WHERE outcome = 'L' and site_id = :siteId UNION SELECT 'W' as outcome, COUNT(*) FROM lottery WHERE outcome = 'W' and site_id = :siteId";
 	//"SELECT outcome, count(*) FROM lottery WHERE site_id =:siteId GROUP BY site_id, outcome";
 	org.hibernate.Query q = getCurrentSession().createSQLQuery(query);
 	q.setInteger("siteId", siteId);
 	return parseLotteryOutcomeMapFromList(q.list());
 }

 @SuppressWarnings("unchecked")
 @Transactional
 public LotteryCustomBean getNextListSlot(){
//	System.out.println("----- getNextSlot");
 	String query="SELECT * FROM lottery WHERE study_subject_id IS NULL ORDER BY lottery_id LIMIT 1";
 	org.hibernate.Query q = getCurrentSession().createSQLQuery(query);
 	return listToLotteryCustomBean(q.list());
 }

 
 private HashMap<String, Integer> parseLotteryOutcomeMapFromList(List<Object[]> list) {
	HashMap<String, Integer> results = new HashMap<String, Integer>();
	if (list.size()>0){
//	    System.out.println("-------- getOutcomeListBySite - list.size="+list.size());
		for(Object[] x: list){
//			System.out.println("X[0]: "+x[0].toString());
//			System.out.println("X[1]: "+x[1].toString());
			results.put((String) x[0], Integer.valueOf(x[1].toString()));
		}
	} else {
//	    System.out.println("-------- getOutcomeListBySite - list is empty");
	    return null;
	}
	return results;
 }
 
 private LotteryCustomBean listToLotteryCustomBean(List<Object[]> list) {
	LotteryCustomBean result = new LotteryCustomBean();
	if (list.size()>0){
//		System.out.println("--- listToLotteryCustomBean - list.size="+list.size());
		if(list.get(0)[0] != null) result.setLotteryId(Integer.valueOf(list.get(0)[0].toString()));
		if(list.get(0)[9] != null) result.setOutcome(list.get(0)[9].toString());
	} else {
//		System.out.println("--- listToLotteryCustomBean - list is empty");
		return null;
	}
	return result;
 }
 
 @Transactional
 public Boolean saveLotteryCustomBean(LotteryCustomBean lcb){
	 if (lcb.getLotteryId()>0 && lcb.getStudySubjectId()>0 && lcb.getSiteId()>0 && lcb.getUserId()>0 && lcb.getUserEmail()!=""){
//		 System.out.println("-------- saveLotteryCustomBean --------"+
//				 "\nUPDATING lottery_id="+lcb.getLotteryId()+
//				 "\nstudy_subject_id="+lcb.getStudySubjectId()+
//				 "\nstudy_subject_label="+lcb.getStudySubjectLabel()+
//				 "\nsite_id="+lcb.getSiteId()+
//				 "\nsite_name="+lcb.getSiteName()+
//				 "\nuser_id="+lcb.getUserId()+
//				 "\nuser_name="+lcb.getUserName()+
//				 "\nuser_email="+lcb.getUserEmail()+
//				 "\noutcome="+lcb.getOutcome());
		 StringBuilder query = new StringBuilder("UPDATE lottery SET study_subject_id=:studySubjectId, site_id=:siteId, user_id=:userId, user_email=:userEmail, date_registered=now() ");
		 if (lcb.getStudySubjectLabel() != "") {query.append(", study_subject_label=:studySubjectLabel ");}
		 if (lcb.getSiteName() != "") {query.append(", site_name=:siteName ");}
		 if (lcb.getUserName() != "") {query.append(", user_name=:userName ");}
		 query.append("WHERE lottery_id=:lotteryId ");
		 
		org.hibernate.Query q;
		try {
			 q = getCurrentSession().createSQLQuery(query.toString());		 
			 q.setInteger("studySubjectId", lcb.getStudySubjectId());
			 q.setInteger("siteId", lcb.getSiteId());
			 q.setInteger("userId", lcb.getUserId());
			 q.setString("userEmail", lcb.getUserEmail());
			 if (lcb.getStudySubjectLabel() != "") {q.setString("studySubjectLabel", lcb.getStudySubjectLabel());}
			 if (lcb.getSiteName() != "") {q.setString("siteName", lcb.getSiteName());}
			 if (lcb.getUserName() != "") {q.setString("userName", lcb.getUserName());}
			 q.setInteger("lotteryId", lcb.getLotteryId());
			 
			 q.executeUpdate();
			 
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		 return true;
	 } else {
		 return false;
	 }	 	 
 }
 
 
 }
