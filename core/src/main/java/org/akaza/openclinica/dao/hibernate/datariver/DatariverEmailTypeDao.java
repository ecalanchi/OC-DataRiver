 package org.akaza.openclinica.dao.hibernate.datariver;
 
 import java.util.ArrayList;
 
 import org.akaza.openclinica.domain.datariver.DatariverEmailTypeBean;
 import org.akaza.openclinica.domain.datariver.DatariverEmailTypeBean;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.springframework.transaction.annotation.Transactional;
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */
 public class DatariverEmailTypeDao {
 	
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
     public ArrayList<DatariverEmailTypeBean> findAll() {
         String query = "from DatariverEmailTypeBean" ;
         org.hibernate.Query q = getCurrentSession().createQuery(query);
        	return (ArrayList<DatariverEmailTypeBean>) q.list();
     }
     
     @Transactional
 	public DatariverEmailTypeBean getDatariverEmailTypeById(int emailTypeId) {
     	String hql = "FROM DatariverEmailTypeBean where emailTypeId=:emailTypeId" ;
     	org.hibernate.Query query = getCurrentSession().createQuery(hql);
     	query.setParameter("emailTypeId", emailTypeId);
     	return (DatariverEmailTypeBean) query.uniqueResult();
 	}
 
    
 }
