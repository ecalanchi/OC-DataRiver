
    /**
     * 
     */
    package org.akaza.openclinica.control.randomization;
    
    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Locale;
    import java.util.ResourceBundle;
    
    import javax.annotation.PostConstruct;
    import javax.servlet.http.HttpServletResponse;
    
    import org.akaza.openclinica.bean.managestudy.StudyGroupBean;
    import org.akaza.openclinica.bean.managestudy.StudyGroupClassBean;
    import org.akaza.openclinica.domain.datariver.RandomizationArmsBean;
    import org.akaza.openclinica.control.AbstractTableFactory;
    import org.akaza.openclinica.control.randomization.RandomizationStatusStatisticView;
    import org.akaza.openclinica.dao.managestudy.StudyGroupDAO;
    import org.akaza.openclinica.dao.hibernate.datariver.RandomizationArmsDao;
    import org.akaza.openclinica.dao.hibernate.datariver.RandomizationSubjectsDao;
    import org.akaza.openclinica.i18n.util.ResourceBundleProvider;
    import org.jmesa.core.filter.DateFilterMatcher;
    import org.jmesa.core.filter.MatcherKey;
    import org.jmesa.facade.TableFacade;
    import org.jmesa.limit.Limit;
    import org.jmesa.util.ItemUtils;
    import org.jmesa.view.component.Row;
    import org.jmesa.view.editor.CellEditor;
    import org.jmesa.view.html.HtmlBuilder;
    import org.jmesa.view.html.renderer.HtmlTableRenderer;
    
    /**
     * @author DataRiver (FB) 06/apr/2012 - added 28/11/2017
     * @version 1.1 14/05/2012 Changed StatisticView : RandomizationStatusStatisticView
     */
    public class RandomizationStatusStatisticsTableFactory extends AbstractTableFactory {
    	 
        private RandomizationArmsDao randomizationArmsDao;
        private RandomizationSubjectsDao randomizationSubjectsDao;
        
        private StudyGroupClassBean studyGroupClassBean;
        private StudyGroupDAO studyGroupDao;
        private StudyGroupBean studyGroupBean;    
    
        private ResourceBundle reswords;
    
    
    	public RandomizationStatusStatisticsTableFactory() {
    		super();
    	}
    
    
    	@Override
    	protected String getTableName() {
    		return "randomizationStatusStatistics";
    	}
    
        @Override
        public void configureTableFacadeCustomView(TableFacade tableFacade) {
            tableFacade.setView(new RandomizationStatusStatisticView(getLocale()));
        }
    
        @Override
        protected void configureColumns(TableFacade tableFacade, Locale locale) {
        	reswords = ResourceBundleProvider.getWordsBundle();
        	tableFacade.setColumnProperties("randomizationArm", "studySubjects", "percentage");
            ((HtmlTableRenderer) tableFacade.getTable().getTableRenderer()).setWidth("375px");
            Row row = tableFacade.getTable().getRow();
            configureColumn(row.getColumn("randomizationArm"), reswords.getString("randomization_arm"), null, null, false, true);
            configureColumn(row.getColumn("studySubjects"), reswords.getString("randomization_arm_subjects"), null, null, false, true);
            configureColumn(row.getColumn("percentage"), reswords.getString("randomization_arm_percentage"), new PercentageCellEditor(), null, false, true);
    
        }
        
        @Override
        public void configureTableFacade(HttpServletResponse response, TableFacade tableFacade) {
            super.configureTableFacade(response, tableFacade);
            tableFacade.addFilterMatcher(new MatcherKey(Date.class, "loginAttemptDate"), new DateFilterMatcher("yyyy-MM-dd hh:mm"));
        }
    
        @SuppressWarnings("unchecked")    
        @Override
        public void setDataAndLimitVariables(TableFacade tableFacade) {
    
            Limit limit = tableFacade.getLimit();
            ArrayList<RandomizationArmsBean> arms = randomizationArmsDao.findAllByStudyGroupClassId(studyGroupClassBean.getId());
            Collection<HashMap<Object, Object>> theItems = new ArrayList<HashMap<Object, Object>>();
    
            if (!limit.isComplete()) {
                int totalRows = arms.size();
                tableFacade.setMaxRows(totalRows);
                tableFacade.setTotalRows(totalRows);
            }
    
            int rowStart = limit.getRowSelect().getRowStart();
            int rowEnd = limit.getRowSelect().getRowEnd();
    
            for (RandomizationArmsBean rab : arms) {
                // Get number of subjects randomized in arms
            	Integer subjectsPerArm = randomizationSubjectsDao.getCountOfSubjectsPerArm(rab.getArm()).intValue();
            	Integer subjectsPerStudyGroupClassId = randomizationSubjectsDao.getCountOfSubjectsPerStudyGroupClassId(studyGroupClassBean.getId()).intValue();
                Long percentage = subjectsPerStudyGroupClassId == 0 ? 0 : Math.round((subjectsPerArm.doubleValue() / subjectsPerStudyGroupClassId.doubleValue()) * 100);
    
                studyGroupBean = (StudyGroupBean) studyGroupDao.findByPK(rab.getArm());
                
                HashMap<Object, Object> theItem = new HashMap<Object, Object>();
                theItem.put("randomizationArm", studyGroupBean.getName());
                theItem.put("studySubjects", subjectsPerArm);
                theItem.put("percentage", String.valueOf(percentage) + "%");
                theItems.add(theItem);
            }
    
            tableFacade.setItems(theItems);
        }
    	
        public RandomizationArmsDao getRandomizationArmsDao() {
    		return randomizationArmsDao;
    	}
    
    	public void setRandomizationArmsDao(RandomizationArmsDao randomizationArmsDao) {
    		this.randomizationArmsDao = randomizationArmsDao;
    	}
    
    	public RandomizationSubjectsDao getRandomizationSubjectsDao() {
    		return randomizationSubjectsDao;
    	}
    
    	public void setRandomizationSubjectsDao(
    			RandomizationSubjectsDao randomizationSubjectsDao) {
    		this.randomizationSubjectsDao = randomizationSubjectsDao;
    	}
    
    	public StudyGroupClassBean getStudyGroupClassBean() {
    		return studyGroupClassBean;
    	}
    
    	public void setStudyGroupClassBean(StudyGroupClassBean studyGroupClassBean) {
    		this.studyGroupClassBean = studyGroupClassBean;
    	}
    
    	public StudyGroupDAO getStudyGroupDao() {
    		return studyGroupDao;
    	}
    
    	public void setStudyGroupDao(StudyGroupDAO studyGroupDao) {
    		this.studyGroupDao = studyGroupDao;
    	}
    
    	public StudyGroupBean getStudyGroupBean() {
    		return studyGroupBean;
    	}
    
    	public void setStudyGroupBean(StudyGroupBean studyGroupBean) {
    		this.studyGroupBean = studyGroupBean;
    	}
    
    	private class PercentageCellEditor implements CellEditor {
            public Object getValue(Object item, String property, int rowcount) {
                Object value = ItemUtils.getItemValue(item, property);
                HtmlBuilder html = new HtmlBuilder();
                html.div().styleClass("graph").close();
                html.div().styleClass("bar").style("width: " + value).close().append(value).divEnd();
                html.divEnd();
                return html.toString();
            }
        }
    
    }
