package org.akaza.openclinica.dao.hibernate.datariver;

import java.util.ArrayList;

import org.akaza.openclinica.domain.datariver.DatariverEnrollmentEnableLogBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class DatariverEnrollmentEnableLogDao {

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
    public ArrayList<DatariverEnrollmentEnableLogBean> findAll() {
        String query = "from DatariverEnrollmentEnableLogBean" ;
        org.hibernate.Query q = getCurrentSession().createQuery(query);
        return (ArrayList<DatariverEnrollmentEnableLogBean>) q.list();
    }
    
    public void insertLog(DatariverEnrollmentEnableLogBean log){
		getCurrentSession().save(log);
    }


    
}
