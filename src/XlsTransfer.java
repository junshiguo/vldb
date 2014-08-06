import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class XlsTransfer {
	
	public static void main(String [] args) throws BiffException, IOException, RowsExceededException, WriteException
	{
		
		String firstname;
		String lastname;
		String university;
		FileWriter out = new FileWriter("name.txt");
		jxl.Workbook workbook = Workbook.getWorkbook(new FileInputStream("reviewer.xls"));
		Sheet sheet = workbook.getSheet("Sheet1");
		int row = sheet.getRows();
		for (int i=0; i<sheet.getRows(); ++i) {
			firstname = sheet.getCell(0,i).getContents();
			if (firstname.equals("eof")) 
				break;
			
			lastname  = sheet.getCell(1,i).getContents();
			university = sheet.getCell(4,i).getContents();
			String content = lastname +" "+firstname+", "+university+"\n";
			System.out.print(content);
			out.write(content);
		}
		out.close();
		System.out.print("done");
		
	}

}
