package org.akaza.openclinica.dao.hibernate.datariver;

import java.math.BigInteger;
import java.util.ArrayList;

import org.akaza.openclinica.domain.datariver.DatariverEnrollmentEnableBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class DatariverEnrollmentEnableDao {

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
    public ArrayList<DatariverEnrollmentEnableBean> findAll() {
        String query = "from DatariverEnrollmentEnableBean" ;
        org.hibernate.Query q = getCurrentSession().createQuery(query);
        return (ArrayList<DatariverEnrollmentEnableBean>) q.list();
    }

    @Transactional
	public void changeEnable(int idToChange, boolean b) {
		DatariverEnrollmentEnableBean cur = this.getByStudyId(idToChange);
		cur.setEnable(b);
		getCurrentSession().save(cur);
	}

    @Transactional
    public DatariverEnrollmentEnableBean getByStudyId(int idToChange) {
		String query = "from DatariverEnrollmentEnableBean where studyId=:stId" ;
        org.hibernate.Query q = getCurrentSession().createQuery(query);
        q.setParameter("stId", idToChange);
        return (DatariverEnrollmentEnableBean) q.uniqueResult();
	}

    @Transactional
	public void updateChildOfAStudy(int idParentToChange, boolean b) {
		String query = "UPDATE datariver_enrollment_enable SET enable = "+b+" WHERE study_id IN ( SELECT study_id FROM study WHERE parent_study_id= "+idParentToChange+" )";
		getCurrentSession().createSQLQuery(query).executeUpdate();
	}
    
    @Transactional
	public boolean isAchildEnable(int idParentToChange) {
		String query = "SELECT COUNT(*) FROM datariver_enrollment_enable WHERE study_id IN ( SELECT study_id FROM study WHERE parent_study_id="+idParentToChange+" )AND enable = true";
		if(((BigInteger)getCurrentSession().createSQLQuery(query).uniqueResult()).intValue() > 0){
			return true;
		}else{
			return false;
		}
	}

    
    /*
     * 2 casi, ha o meno il parent_study_id
     * se ce l'ha si prende il valore dal DB
     * se non ce l'ha bisogna controllare se esiste almeno un figlio abilitato
     */
    @Transactional
	public boolean isEnableStudy(int parentStudyId, int id) {
		if(parentStudyId>0){
			//caso che in cui ha il padre e quindi si setta semplicemente il valore
			return getByStudyId(id).isEnable();
		}else{
			//caso che sia il padre, si controlla che ci sia almeno un figlio enable
			return isAchildEnable(id);
		}
	}
    
}
