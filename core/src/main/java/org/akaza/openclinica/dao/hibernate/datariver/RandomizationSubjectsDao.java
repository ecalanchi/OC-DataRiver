	package org.akaza.openclinica.dao.hibernate.datariver;
	
	import java.math.BigInteger;
	import java.util.ArrayList;
	
	import org.akaza.openclinica.domain.datariver.RandomizationSubjectsBean;
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.springframework.transaction.annotation.Transactional;
	
	 /**
	  * @author DataRiver (EC) 28/11/2017
	  *
	  */
	public class RandomizationSubjectsDao {
	
		
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
		public ArrayList<RandomizationSubjectsBean> findAll() {
			String query = "from RandomizationSubjectsBean" ;
			org.hibernate.Query q = getCurrentSession().createQuery(query);
			return (ArrayList<RandomizationSubjectsBean>) q.list();
		}
		
		@Transactional
		public RandomizationSubjectsBean findByGroupClassIdAndSubjectId(int studyGroupClassID, int studySubjectID) {
			String query="SELECT randomization_subjects.* FROM randomization_subjects, randomization_arms WHERE randomization_subjects.arm = randomization_arms.arm AND randomization_arms.study_group_class_id = ? AND randomization_subjects.study_subject_id = ?";
			org.hibernate.Query q = getCurrentSession().createSQLQuery(query).addEntity(RandomizationSubjectsBean.class);
			q.setInteger(0, studyGroupClassID);
			q.setInteger(1, studySubjectID);
			return (RandomizationSubjectsBean) q.uniqueResult();
		}
		
		@Transactional
		public Long getCountOfSubjectsBasedOnArmAndStratificationVarValues(int arm, String stratificationVarValues){
			org.hibernate.Query q=getCurrentSession().createQuery("select count(*) From RandomizationSubjectsBean where arm = :arm AND stratificationVarValues = :stratificationVarValues");
			q.setInteger("arm", arm);
			q.setString("stratificationVarValues", stratificationVarValues);
			return (Long)q.uniqueResult();
		}
		
		@Transactional
		public Long getCountOfSubjectsPerArm(int arm) {
			org.hibernate.Query q=getCurrentSession().createQuery("select count(*) From RandomizationSubjectsBean where arm = :arm");
			q.setInteger("arm", arm);
			return (Long)q.uniqueResult();
		}
		
		@Transactional
		public BigInteger getCountOfSubjectsPerStudyGroupClassId(int studyGroupClassId) {
			org.hibernate.Query q=getCurrentSession().createSQLQuery("SELECT COUNT(*) from randomization_subjects s JOIN randomization_arms a ON s.arm = a.arm WHERE a.study_group_class_id = ?");
			q.setInteger(0, studyGroupClassId);
			return (BigInteger)q.uniqueResult();
		}
		
		
		
		@Transactional
		public void create(RandomizationSubjectsBean randomizationSubject) {
			getSessionFactory().getStatistics().logSummary();
			getCurrentSession().saveOrUpdate(randomizationSubject);
		}
		
		
		
		
	}
