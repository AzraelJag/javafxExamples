package MyTableView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadWriter {
	
	public static void readXLSXFileToAccountTable(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		InputStream ExcelFileToRead = new FileInputStream(dateiname);

		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell =  null;

		String id = new String();
		String name = new String();
		String pw = new String();
		String wallet = new String();
		String balance  = new String();
		String zellInhalt = new String();
		
		Iterator rows = sheet.rowIterator();
		
		int i = 0; //Spalte
		int j = 0; //Zeile
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			Boolean zeilenende = false;
			i=0;
//			while (cells.hasNext()){
			while (i<100 && !zeilenende){
//				System.out.println("i:" + i);
				if (j>0){						//erste Zeile überlesen (Überschriften)
//					System.out.println("j:" + j);
					// leere Zelle auf leer setzen
					if (sheet.getRow(j).getCell(i) == null) {
						cell = sheet.getRow(0).getCell(0);
						cell.setCellValue(" ");
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						if (i>3) {
							zeilenende = true;
						}
					}else{
						cell=(XSSFCell) cells.next();
						}
					
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						zellInhalt=cell.getStringCellValue().trim();
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						zellInhalt=(new Double(cell.getNumericCellValue()).toString());
					}
						else{
						//U Can Handel Boolean, Formula, Errors
					}
				
					switch (i) {
						case 0:
							id=zellInhalt.trim();						
							break;
						case 1:
							name=zellInhalt.trim();		
							break;
						case 2:
							pw=zellInhalt.trim();		
							break;
						case 3:
							wallet=zellInhalt.trim();		
							break;
						case 4:
							balance=zellInhalt.trim();		
							break;
						default:
//							System.out.println("Inhalt:"+zellInhalt+":");
						 	if (zeilenende) {
						 		//put Account
						 			MyTableView.addAccount(new Account(
					               		id,
					               		name,
					               		pw,
					               		wallet,
					               		balance));
						 	} 
						 	break;
					}
				}
				i++;
			}
			j++;
		}
		wb.close();
	}
	
	
	public static void writeAccountTableToXLSXFile(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		
		String sheetName = "Accounts";

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating i number of rows
	    Account ausg;

	    String id = new String();
		String name = new String();
		String pw = new String();
		String wallet = new String();
		String balance  = new String();

		XSSFCell cell;
		
		// Spaltenüberschriften
	    XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell = row.createCell(2);
		cell.setCellValue("Passwort");
		cell = row.createCell(3);
		cell.setCellValue("Walletadresse");
		cell = row.createCell(4);
		cell.setCellValue("Anzahl Token");
	
		for (int i=0; i<MyTableView.getAccounts().size() ; i++){
			ausg 	= MyTableView.getAccounts().get(i);
			
        	id 		= 	ausg.getId();
        	name 	= 	ausg.getName();
        	pw 		= 	ausg.getPw();
         	wallet 	= 	ausg.getWalletAddress();
         	balance = 	ausg.getBalance().toString();
         	
			row = sheet.createRow(i+1);

			cell = row.createCell(0);
			cell.setCellValue(id);
			cell = row.createCell(1);
			cell.setCellValue(name);
			cell = row.createCell(2);
			cell.setCellValue(pw);
			cell = row.createCell(3);
			cell.setCellValue(wallet);
			cell = row.createCell(4);
			cell.setCellValue(balance);
		}

		FileOutputStream fileOut = new FileOutputStream(dateiname);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		wb.close();
		fileOut.flush();
		fileOut.close();
	}
}
