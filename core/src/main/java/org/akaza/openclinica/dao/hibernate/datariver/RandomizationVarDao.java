    package org.akaza.openclinica.dao.hibernate.datariver;
    
    import java.util.ArrayList;
    
    import org.akaza.openclinica.domain.datariver.RandomizationArmsBean;
    import org.akaza.openclinica.domain.datariver.RandomizationVarBean;
    import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.springframework.transaction.annotation.Transactional;
    
    /**
     * @author DataRiver (EC) 28/11/2017
     *
     */    
    public class RandomizationVarDao {
    
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
        public ArrayList<RandomizationVarBean> findAll() {
            String query = "from RandomizationVarBean" ;
            org.hibernate.Query q = getCurrentSession().createQuery(query);
            return (ArrayList<RandomizationVarBean>) q.list();
        }
        
        @Transactional
        public RandomizationVarBean findByPk(Integer studyGroupClassId){
        	getSessionFactory().getStatistics().logSummary();
            String query = "from RandomizationVarBean where studyGroupClassId = :studyGroupClassId";
            org.hibernate.Query q = getCurrentSession().createQuery(query);
            q.setInteger("studyGroupClassId", studyGroupClassId);
            return (RandomizationVarBean) q.uniqueResult();
        }
        
        /**
    	 * Return all the "randomization sets" associated to study_event_definition_id and crf_id from randomization_var table.
    	 * The same pair of study_event_definition_id and crf_id can be the entry point for more than one randomization set. 
    	 * 
    	 * @param studyEventDefinitionID
    	 * @param crfID
    	 * @return
    	 */
        @Transactional
    	public ArrayList<RandomizationVarBean> findAllByStudyEventDefinitionIdAndCrfId(int studyEventDefinitionID, int crfID){
    		String query = "select * from randomization_var WHERE study_event_definition_id = ? AND crf_id = ?" ;
            org.hibernate.Query q = getCurrentSession().createSQLQuery(query).addEntity(RandomizationVarBean.class);
            q.setInteger(0, studyEventDefinitionID);
            q.setInteger(1, crfID);
            
            return (ArrayList<RandomizationVarBean>) q.list();
    	}
       
        /**
         * Check if the pair of study_event_definition_id and crf_id requires any randomization. 
         * 
         * @param studyEventDefinitionID
         * @param crfID
         * @return
         */
          @Transactional
        public Boolean toBeRandomized(int studyEventDefinitionID, int crfID){
        	String query = "select * from randomization_var WHERE study_event_definition_id = ? AND crf_id = ?" ;
              org.hibernate.Query q = getCurrentSession().createSQLQuery(query).addEntity(RandomizationVarBean.class);
              q.setInteger(0, studyEventDefinitionID);
              q.setInteger(1, crfID);
              
              if (q.list().isEmpty()) 
              	return false;
              else 
              	return true;
        }  
        
    	
    }
