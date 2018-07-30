 package org.akaza.openclinica.dao.hibernate.datariver;
 
 import java.util.ArrayList;
 
 import org.akaza.openclinica.domain.datariver.DatariverEmailBean;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.springframework.transaction.annotation.Transactional;
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */
 public class DatariverEmailDao {
 	
 	private SessionFactory sessionFactory;
 
 	
     /**
      * @return the sessionFactory
      */
     public SessionFactory getSessionFactory() {
         return sessionFactory;
     }
 
     /**
      * @param sessionFactory
      * the sessionFactory to set
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
 	public ArrayList<DatariverEmailBean> getDatariverEmail(int crfId, int eventDefinitionCrfId) {
     	String hql = "FROM DatariverEmailBean where crfId=:crfId and eventDefinitionCrfId=:eventDefinitionCrfId and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("crfId", crfId);
     	query.setParameter("eventDefinitionCrfId", eventDefinitionCrfId);
    		return (ArrayList<DatariverEmailBean>) query.list();
 	}
     
     @SuppressWarnings("unchecked")
     @Transactional
 	public ArrayList<DatariverEmailBean> getDatariverEmail(int crfId, int eventDefinitionCrfId, int emailTypeId) {
     	String hql = "FROM DatariverEmailBean where crfId=:crfId and eventDefinitionCrfId=:eventDefinitionCrfId and emailTypeId=:emailTypeId and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("crfId", crfId);
     	query.setParameter("eventDefinitionCrfId", eventDefinitionCrfId);
     	query.setParameter("emailTypeId", emailTypeId);
     	return (ArrayList<DatariverEmailBean>) query.list();
 	}
     
     @SuppressWarnings("unchecked")
 	@Transactional
 	public ArrayList<DatariverEmailBean> getDatariverEmail(int crfId, int eventDefinitionCrfId, int emailTypeId, int studyGroupId) {
     	String hql = "FROM DatariverEmailBean where crfId=:crfId and eventDefinitionCrfId=:eventDefinitionCrfId and emailTypeId=:emailTypeId and studyGroupId=:studyGroupId and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("crfId", crfId);
     	query.setParameter("eventDefinitionCrfId", eventDefinitionCrfId);
     	query.setParameter("emailTypeId", emailTypeId);
     	query.setParameter("studyGroupId", studyGroupId);
     	return (ArrayList<DatariverEmailBean>) query.list();
 	}
     
 	/**
 	 * Currently not used
 	 * @param studyId
 	 * @return
 	 */
 	@SuppressWarnings("unchecked")
 	@Transactional
 	public ArrayList<DatariverEmailBean> getNewUserDatariverEmail(int studyId) {
     	String hql = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=3 and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("studyId", studyId);
     	return (ArrayList<DatariverEmailBean>) query.list();
 	}
 	
 	@Transactional
 	public DatariverEmailBean getNewUserDatariverEmail() {
     	String hql = "FROM DatariverEmailBean where studyId is null and emailTypeId=3 and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	return (DatariverEmailBean) query.uniqueResult();
 	}
 	
 //	@Transactional
 //	public ArrayList<DatariverEmailBean> getNewUserDatariverEmail(int studyId) {
 //    	String hql1 = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=3 and enabled=true" ;
 //    	org.hibernate.Query query1 = getCurrentSession().createQuery(hql1);
 //    	query1.setParameter("studyId", studyId);
 //    	ArrayList<DatariverEmailBean> rs = new ArrayList<DatariverEmailBean>(); 
 //    	rs.add((DatariverEmailBean) query1.uniqueResult());
 //    	String hql2 = "FROM DatariverEmailBean where studyId is null and emailTypeId=3 and enabled=true" ;
 //    	org.hibernate.Query query2 = getCurrentSession().createQuery(hql2);
 //    	query2.setParameter("studyId", studyId);
 //    	rs.add((DatariverEmailBean) query2.uniqueResult());
 //    	return rs;
 //	}
 	
 	/**
 	 * Currently not used
 	 * @param studyId
 	 * @return
 	 */
 	@SuppressWarnings("unchecked")
 	@Transactional
 	public ArrayList<DatariverEmailBean> getNewUserNotificationDatariverEmail(int studyId) {
     	String hql = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=4 and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("studyId", studyId);
     	return (ArrayList<DatariverEmailBean>) query.list();
 	}
 	
 	@Transactional
 	public DatariverEmailBean getNewUserNotificationDatariverEmail() {
     	String hql = "FROM DatariverEmailBean where studyId is null and emailTypeId=4 and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	return (DatariverEmailBean) query.uniqueResult();
 	}
     
 //	@Transactional
 //	public ArrayList<DatariverEmailBean> getNewUserNotificationDatariverEmail(int studyId) {
 //    	String hql1 = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=4 and enabled=true" ;
 //    	org.hibernate.Query query1 = getCurrentSession().createQuery(hql1);
 //    	query1.setParameter("studyId", studyId);
 //    	ArrayList<DatariverEmailBean> rs = new ArrayList<DatariverEmailBean>(); 
 //    	rs.add((DatariverEmailBean) query1.uniqueResult());
 //    	String hql2 = "FROM DatariverEmailBean where studyId is null and emailTypeId=4 and enabled=true" ;
 //    	org.hibernate.Query query2 = getCurrentSession().createQuery(hql2);
 //    	query2.setParameter("studyId", studyId);
 //    	rs.add((DatariverEmailBean) query2.uniqueResult());
 //    	return rs;
 //    }	
     
 	/**
 	 * Get email (unique by study) for administrative editing changes.
 	 * @author DataRiver (EC) 02/05/2018
 	 * @param studyId
 	 * @return
 	 */
 	@Transactional
 	public DatariverEmailBean getDatariverEmailAdminEdit(int studyId) {
     	String hql = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=6 and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("studyId", studyId);
     	return (DatariverEmailBean) query.uniqueResult();
 	}
 	
 	@Transactional
 	public DatariverEmailBean getDatariverEmailEnrollment(int studyId, int emailTypeId) {
     	String hql = "FROM DatariverEmailBean where studyId=:studyId and emailTypeId=:emailTypeId and enabled=true" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("studyId", studyId);
     	query.setParameter("emailTypeId", emailTypeId);
     	return (DatariverEmailBean) query.uniqueResult();
 	}
 	
    @Transactional
   	public DatariverEmailBean getDatariverEmailTest() {
       	String hql = "FROM DatariverEmailBean where emailTypeId=5 and enabled=true" ;
       	org.hibernate.Query query = getCurrentSession().createQuery(hql);
       	return (DatariverEmailBean) query.uniqueResult();
   	} 	
 
 }
