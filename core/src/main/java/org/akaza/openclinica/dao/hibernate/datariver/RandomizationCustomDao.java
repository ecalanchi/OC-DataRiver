 package org.akaza.openclinica.dao.hibernate.datariver;
 
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 
 import org.akaza.openclinica.domain.datariver.RandomizationCustomBean;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.springframework.transaction.annotation.Transactional;
 
 /**
  * @author DataRiver (EC) 28/11/2017
  *
  */
 public class RandomizationCustomDao {
 	
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
     
     @Transactional
     public RandomizationCustomBean findRandomizationListArm(String studyRandCode){
     	String query="SELECT * FROM " + studyRandCode + " WHERE id_paziente IS NULL ORDER BY id LIMIT 1";
     	org.hibernate.Query q = getCurrentSession().createSQLQuery(query);
     	return parseCustomRanBeanFromList(q.list());
     }
     
 
 
 	@Transactional
     public RandomizationCustomBean findRandomizationListArmWithStrato(String studyRandCode,String strato){
     	String query="SELECT * FROM " + studyRandCode + " WHERE strato = ? AND id_paziente IS NULL ORDER BY id LIMIT 1";
     	org.hibernate.Query q = getCurrentSession().createSQLQuery(query);
     	q.setString(0, strato);
     	return parseCustomRanBeanFromList(q.list());
     }
 
     @Transactional
     public void updateRandomizationList(String randomListName,
 			int studySubjectId, int Userid, Integer fileListRandomizationId) {
     	String query="UPDATE " + randomListName + " SET id_paziente = ?, data_randomizzazione = now(), id_utente = ?  where id = ?";
     	org.hibernate.Query q = getCurrentSession().createSQLQuery(query);
     	q.setInteger(0, studySubjectId);
     	q.setInteger(1, Userid);
     	q.setInteger(2, fileListRandomizationId);
     	q.executeUpdate();
     	
 		
 	}
 
 	private ArrayList<RandomizationCustomBean> parseCustomRanBeans(List<Object[]> list) {
 		ArrayList<RandomizationCustomBean> results=new ArrayList<RandomizationCustomBean>();
 		for(Object[] current : list){
 //			id integer NOT NULL,
 //			  strato character varying(7),
 //			  randomizzazione integer,
 //			  id_paziente integer,
 //			  data_randomizzazione timestamp(6) without time zone,
 //			  id_utente integer
 			RandomizationCustomBean curBean=new RandomizationCustomBean();
 			if(current[0] != null)curBean.setId((Integer) current[0]);
 			if(current[1] != null)curBean.setStrato((String)current[1]);
 			if(current[2] != null)curBean.setRandomizzazione((Integer)current[2]);
 			if(current[3] != null)curBean.setIdPaziente((Integer)current[3]);
 			if(current[4] != null)curBean.setDataRandomizzazione((Date)current[4]);
 			if(current[5] != null)curBean.setIdUtente((Integer)current[5]);
 //			System.out.println(curBean.getDataRandomizzazione().toString());
 			results.add(curBean);
 		}
 		return results;
 
 	}
 	
 	
     private RandomizationCustomBean parseCustomRanBeanFromList(List<Object[]> list) {
     	RandomizationCustomBean result=new RandomizationCustomBean();
     	if(list.get(0)[0] != null)result.setId((Integer) list.get(0)[0]);
 		if(list.get(0)[1] != null)result.setStrato((String)list.get(0)[1]);
 		if(list.get(0)[2] != null)result.setRandomizzazione((Integer)list.get(0)[2]);
 		if(list.get(0)[3] != null)result.setIdPaziente((Integer)list.get(0)[3]);
 		if(list.get(0)[4] != null)result.setDataRandomizzazione((Date)list.get(0)[4]);
 		if(list.get(0)[5] != null)result.setIdUtente((Integer)list.get(0)[5]);
 		
 		return result;
 	}
 
 
 
 }
