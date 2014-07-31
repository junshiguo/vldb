import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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


class pinfo {
	
	public String title;
	public String author;
	public String abstr;
	public String session;
	public String link;
	
	public pinfo(String session,String title, String author,String abstr,String link)
	{
		this.title = title;
		this.author = author;
		this.abstr = abstr;
		this.session = session;
		this.link=link;
	}
	
}

public class XlsTransfer {
	
	public static void main(String [] args) throws BiffException, IOException, RowsExceededException, WriteException
	{
		ArrayList<pinfo> paperlist = new ArrayList<pinfo>();
		String title,link,abs, session, author;
		Map<String, String> linkinfo = new HashMap<String,String>();
		Map<String, String> absinfo = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(new File("final_result.txt")));
		while ((title = reader.readLine()) != null) {
			link = reader.readLine();
			linkinfo.put(title, link);
		}
		
		jxl.Workbook workbook = Workbook.getWorkbook(new FileInputStream("papers.xls"));
		Sheet sheet = workbook.getSheet("Research Vol 6");
		int row = sheet.getRows();
		for (int i=1; i<sheet.getRows(); ++i) {
			title = sheet.getCell(1,i).getContents();
			if (title.equals("eof")) {
				break;
			}
			abs   = sheet.getCell(3,i).getContents();
			absinfo.put(title, abs);
		}
		sheet = workbook.getSheet("Research Vol 7");
		for (int r = 1; r<sheet.getRows(); ++r) {
			title = sheet.getCell(1,r).getContents();
			if (title.equals("eof")) {
				break;
			}
			abs   = sheet.getCell(3,r).getContents();
			absinfo.put(title, abs);
		}
		//sheet = workbook.getSheet(arg0)
		workbook.close();
		workbook = Workbook.getWorkbook(new FileInputStream("program.xls"));
		sheet = workbook.getSheet("Sheet4");
		for (int i=1; i<sheet.getRows(); ++i) {
			session = sheet.getCell(0,i).getContents();
			if (session.equals("eof")) break;
			title = sheet.getCell(1,i).getContents();
			author = sheet.getCell(2,i).getContents();
			abs = absinfo.get(title);
			link = linkinfo.get(title);
			if(link == null) link = "none";
			paperlist.add(new pinfo("Papers "+session, title, author, abs,link));
		}
		/*
		System.out.println(paperlist.size());
		for(pinfo p: paperlist) {
			System.out.println(p.session);
			System.out.println(p.link);
		}*/
		
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("tmp.xls"));	
		jxl.write.WritableSheet ws = wwb.createSheet("Test Shee 1", 0);
		jxl.write.Label label_session, label_id, label_title, label_link, label_author,label_abstract;
		
		int c = 96;
		for(int i = 0; i<paperlist.size(); ++i) {
			pinfo t = paperlist.get(i);
			label_session = new jxl.write.Label(0, i, t.session);
			label_id      = new jxl.write.Label(1, i, (new Integer(c)).toString());
			c=c+1;
			label_link    = new jxl.write.Label(2, i, t.link);
			label_title   = new jxl.write.Label(3, i, t.title);
			label_author  = new jxl.write.Label(4, i, t.author);
			label_abstract = new jxl.write.Label(5, i, t.abstr);
			ws.addCell(label_session);
			ws.addCell(label_id);
			ws.addCell(label_title);
			ws.addCell(label_link);
			ws.addCell(label_author);
			ws.addCell(label_abstract);
		}
	    wwb.write();
	    wwb.close();
	}

}
