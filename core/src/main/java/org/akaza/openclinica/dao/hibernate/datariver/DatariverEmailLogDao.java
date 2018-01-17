 package org.akaza.openclinica.dao.hibernate.datariver;
 
 import java.util.ArrayList;
 
 import org.akaza.openclinica.domain.datariver.DatariverEmailLogBean;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.springframework.transaction.annotation.Transactional;
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */ 
 public class DatariverEmailLogDao {
 	
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
     public ArrayList<DatariverEmailLogBean> findAll() {
         String query = "from DatariverEmailLogBean" ;
         org.hibernate.Query q = getCurrentSession().createQuery(query);
        	return (ArrayList<DatariverEmailLogBean>) q.list();        
     }
     
     @Transactional 
     public void insertLog(DatariverEmailLogBean log){
 		getCurrentSession().save(log);
     }
 
 
    
 }
