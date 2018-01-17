    package org.akaza.openclinica.control.randomization;
    
    /**
    * @author DataRiver (FB) 28/11/2017
    * Classe che genera e salva il file Excel a partire dagli oggetti che vengono forniti dall'interfaccia utente.
    *
    *
    *
    */
    
    import java.io.File;
    import java.io.FileInputStream;
    import java.util.Iterator;
    import java.util.Vector;
    
    import org.akaza.openclinica.dao.core.CoreResources;
    import org.apache.poi.hssf.usermodel.HSSFCell;
    import org.apache.poi.hssf.usermodel.HSSFRow;
    import org.apache.poi.hssf.usermodel.HSSFSheet;
    import org.apache.poi.hssf.usermodel.HSSFWorkbook;
    import org.apache.poi.poifs.filesystem.POIFSFileSystem;
    
    public class ExcelFileList {
    
    	 private static String[] head={"ID","Strato","Randomizzazione","ID Paziente","Data randomizzazione"};
    //	 protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
    //	 private static final String PATH = "/usr/local/java/apache-tomcat-6.0.35/webapps/OpenClinica/WEB-INF/classes/Randomization/";
    //	 private static final String PATH = "C:\\Users\\Enrico\\Randomization\\";
    
    	 private FileInputStream fInput;
    	 private String fileName = "";
    
    	 
    	 public ExcelFileList(String protocolName){
    		 
    		 System.out.println(CoreResources.getField("filePath"));
    		 fileName = CoreResources.getField("filePath") + protocolName.toLowerCase() + File.separator + protocolName.toLowerCase() + "_randomization.xls";
    		 System.out.println(fileName);
    
    	 }
    	 
    	 public Vector searchExcelFile(String IdPaziente, String Strato)
    	    {
    	        /* Define a Vector of cells
    	         */
    	        Vector cellVectorHolder = new Vector();
    	 
    
    	        return cellVectorHolder;
    	    }
    	 
    	 public void openFileInputStream(){
    		 try {
    
    	        /** Creating Input Stream**/
    	        //InputStream myInput= ReadExcelFile.class.getResourceAsStream( fileName );
    	        fInput = new FileInputStream(fileName);
    	 
    	        /** Create a POIFSFileSystem object**/
    	        POIFSFileSystem myFileSystem = new POIFSFileSystem(fInput);
    	 
    	        /** Create a workbook using the File System**/
    	         HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
    	 
    	         /** Get the first sheet from workbook**/
    	        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
    	 
    	        /** We now need something to iterate through the cells.**/
    	          Iterator rowIter = mySheet.rowIterator();
    	 
    	          while(rowIter.hasNext()){
    	              HSSFRow myRow = (HSSFRow) rowIter.next();
    	              Iterator cellIter = myRow.cellIterator();
    	              Vector cellStoreVector=new Vector();
    	              while(cellIter.hasNext()){
    	                  HSSFCell myCell = (HSSFCell) cellIter.next();
    	                  cellStoreVector.addElement(myCell);
    	                  System.out.println(myCell.toString());
    	              }
    	          }
    	        }
    		 catch (Exception e){e.printStackTrace(); }
    		 
    	 }
    	 
    	 private void closeFileInputStrean(){
    		 
    	 }
    	 
    	    private static void printCellDataToConsole(Vector dataHolder) {
    	 
    	        for (int i=0;i<dataHolder.size();i++) {                  
    	                      Vector cellStoreVector=(Vector)dataHolder.elementAt(i);
    	            for (int j=0; j< cellStoreVector.size();j++){
    	                HSSFCell myCell = (HSSFCell)cellStoreVector.elementAt(j);
    	                String stringCellValue = myCell.toString();
    	                System.out.print(stringCellValue+"\t");
    	            }
    	            System.out.println();
    	        }
    	    }      
    
    //	 public String ExcelFileList(String[] pazienti, String fileName) throws Exception{
    //
    //	 // Header e creazione excel
    //
    //	 Workbook wb = new HSSFWorkbook();
    //
    //	 Sheet sheet1 = wb.createSheet("Result");
    //
    //	 CreationHelper createHelper = wb.getCreationHelper();
    //
    //	 CellStyle style = wb.createCellStyle();
    //	 Font font = wb.createFont();
    //	 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    //	 font.setFontHeightInPoints((short)12);
    //	 style.setFont(font);
    //
    //	 Row header = sheet1.createRow((short)0);
    //
    //
    //	 for (int i=0; i < head.length; i++){
    //	 Cell cell = header.createCell(i);
    //	 cell.setCellValue(head[i]);
    //	 cell.setCellStyle(style);
    //	 }
    //
    //
    //	 sheet1.getRow(0).setHeightInPoints((short)14);
    //
    //	 for(int i=0;i<pazienti.length;i++){
    //	 Row row = sheet1.createRow((short)i+1);
    //	 Cell cell = row.createCell(0);
    //	 cell.setCellValue(i+1);
    //
    //	 cell = row.createCell(1);
    //	 cell.setCellValue(pazienti[i].getStudyName());
    //
    //	 cell = row.createCell(2);
    //	 cell.setCellValue(pazienti[i].getCenterName());
    //
    //	 cell = row.createCell(3);
    //	 cell.setCellValue(pazienti[i].getSex()==1?"M":"F");
    //
    //	 CellStyle cellStyle = wb.createCellStyle();
    //	 cellStyle.setDataFormat(
    //	 createHelper.createDataFormat().getFormat("yyyy-MMM-dd"));
    //
    //	 cell = row.createCell(4);
    //	 cell.setCellValue(pazienti[i].getDateOfBirth());
    //	 cell.setCellStyle(cellStyle);
    //
    //	 cell = row.createCell(5);
    //	 cell.setCellValue(pazienti[i].getDiagnosisDate());
    //	 cell.setCellStyle(cellStyle);
    //
    //
    //	 cell = row.createCell(6);
    //	 cell.setCellValue(pazienti[i].getRegistrationDate());
    //	 cell.setCellStyle(cellStyle);
    //
    //	 cell = row.createCell(7);
    //	 cell.setCellValue(pazienti[i].getRegistrationAge());
    //
    //	 cell = row.createCell(8);
    //	 cell.setCellValue(pazienti[i].getIcdo3Code());
    //
    //	 cell = row.createCell(9);
    //	 cell.setCellValue(pazienti[i].getGroupName());
    //
    //	 cell = row.createCell(10);
    //	 cell.setCellValue(pazienti[i].getHistotype());
    //
    //
    //	 }
    //
    //	 for(int i=0;i<head.length;i++){
    //	 sheet1.autoSizeColumn(i);
    //	 }
    //
    //
    //	 String fileN=fileName+".xls";
    //	 FileOutputStream fileOut=null;
    //
    //	 // Retrieve the servlet context
    //	 ServletContext servletContext = ContextLoaderListener.getCurrentWebApplicationContext().getServletContext();
    //	 // get the tempdir
    //	 File tempDir = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
    //
    ////	 log.info("Servlet context path: " + tempDir.getAbsolutePath());
    //
    //	 if (!tempDir.exists()){
    ////	 log.info("temp dir does not exist");
    //	 boolean created = tempDir.mkdir();
    //	 if (created){
    ////	 log.info("Created direcory temp in: " + tempDir.getAbsolutePath());
    //
    //	 }
    //	 }
    //
    //
    //	 try {
    //
    //	 fileOut = new FileOutputStream(tempDir.getAbsolutePath() + File.separator + fileN);
    //
    //	 } catch (FileNotFoundException e) {
    //
    ////	 log.error("Error creating the Excel file, FileNotFoundException: " + " Stack trace" + e.getMessage());
    //	 throw new ExcelException("There was a problem exporting the result in excel, please try again!", e);
    //
    //	 } catch (SecurityException es){
    ////	 log.error("Error creating the Excel file, SecurityException: " + " Stack trace" + es.getMessage());
    //
    //	 throw new ExcelException("There was a problem exporting the result in excel, please try again!", es);
    //	 }
    //
    //	 try {
    //	 wb.write(fileOut);
    //	 } catch (IOException e) {
    //
    ////	 log.error("Error writing the Excel file, IOException: " + " Stack trace" + e.getMessage());
    //	 throw new ExcelException("There was a problem exporting the result in excel, please try again!", e);
    //	 }
    //
    //	 try {
    //	 fileOut.close();
    //	 } catch (IOException e) {
    ////	 log.error("Error closing the Excel file, IOException: " + " Stack trace" + e.getMessage());
    //	 }
    //
    //	 return fileName;
    //
    //	 }
    
    }
