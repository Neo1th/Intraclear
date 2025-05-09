package utilities;

import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadXls {
	
	
	public static Map<String , String> getRowData(String filepath, String sheetName,int rowNum) throws EncryptedDocumentException, IOException{
		
		Map<String , String> rowData = new HashMap<>()	;
		
		try(FileInputStream fis=new FileInputStream("/Users/apple/eclipse-workspace/Intraclear/src/test/resources/testdata/Testdata.xlsx")){
		Workbook workbook =WorkbookFactory.create(fis);
		
		
		Sheet sheet=workbook.getSheet(sheetName);
		if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist in file: " + filepath);
        }
		Row headerRow=sheet.getRow(0);
		Row dataRow=sheet.getRow(rowNum);

	
	 for(int i=0;i<=headerRow.getLastCellNum();i++) {
		 
		 Cell headerCell = headerRow.getCell(i);
		 if(headerCell==null)continue;
		 
		 Cell dataCell = dataRow.getCell(i);		
		 
		 String key =headerCell.toString();
		 
		 String value;
		 
		 if(dataCell!=null) {
			 value =dataCell.toString();
			 
		 }
		 else {
			 value= "";
		 }
		 
		rowData.put(key, value);
	 }
}  
	catch(IOException e)	{
		e.printStackTrace();
	}
	return rowData;
   }
}
	