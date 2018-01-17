	package org.akaza.openclinica.dao.hibernate.datariver;
	
	import java.util.ArrayList;
	
	
	
	import org.akaza.openclinica.domain.datariver.RandomizationArmsBean;
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.springframework.transaction.annotation.Transactional;
	
	 /**
	  * @author DataRiver (EC) 28/11/2017
	  *
	  */	
	public class RandomizationArmsDao {
	
		
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
		public ArrayList<RandomizationArmsBean> findAll() {
			String query = "from RandomizationArmsBean" ;
			org.hibernate.Query q = getCurrentSession().createQuery(query);
			return (ArrayList<RandomizationArmsBean>) q.list();
		}
		
		@Transactional
		public RandomizationArmsBean findByPk(Integer arm){
			getSessionFactory().getStatistics().logSummary();
			String query = "from RandomizationArmsBean rarms where rarms.arm = :arm";
			org.hibernate.Query q = getCurrentSession().createQuery(query);
			q.setInteger("arm", arm);
			return (RandomizationArmsBean) q.uniqueResult();
		}
		
		
		/**
		* Get arms for current randomization set (study_group_class_id)
		* @return
		*/
		@Transactional
		public ArrayList<RandomizationArmsBean> findAllByStudyGroupClassId(int studyGroupClassId) {
			String query = "from RandomizationArmsBean rarms where rarms.studyGroupClassId = :studyGroupClassId" ;
			org.hibernate.Query q = getCurrentSession().createQuery(query);
			q.setInteger("studyGroupClassId", studyGroupClassId);
			return (ArrayList<RandomizationArmsBean>) q.list();
		}
		
		
	}
