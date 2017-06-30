
 package org.akaza.openclinica.dao.hibernate.datariver;
 
 import org.akaza.openclinica.domain.datariver.StudySubjectCustomLabelBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
 
 
 /**
  * @author Enrico Calanchi 17/dic/2014
  *
  */
 public class StudySubjectCustomLabelDao {
 	
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
 
     
     @Transactional
     public StudySubjectCustomLabelBean getByStudyId(int studyId) {
 		String hql = "FROM StudySubjectCustomLabelBean where studyId=:stId" ;
         org.hibernate.Query query = getCurrentSession().createQuery(hql);
         query.setParameter("stId", studyId);
         return (StudySubjectCustomLabelBean) query.uniqueResult();
 	}
     
     @Transactional
 	public void incrementStudyLabelCounter(int studyId) {
 		String query = "UPDATE study_subject_custom_label SET study_label_counter = study_label_counter + 1 WHERE study_id = "+studyId+" ";
 		getCurrentSession().createSQLQuery(query).executeUpdate();
 	}
 }