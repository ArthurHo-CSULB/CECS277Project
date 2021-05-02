package cecs277;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MyFileList extends MyFileNode {
	
	private SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
	private DecimalFormat decFmt = new DecimalFormat("#,###");
	
	
	public MyFileList(String filename) {
		super(filename);
	}
	
	public MyFileList(String name, File f) {
		super(name, f);
	}
	
	public String toString() {
		if (App.detailed == false || super.isDirectory()) {
			String[] seperated = getFileName().split("[\\\\]");
			return seperated[seperated.length-1];
		}
		else {
			return String.format("%-40s%-12s%-10s", super.toString(), 
					dateFmt.format(getFile().lastModified()) +"", 
					decFmt.format(getFile().length()/1024/1024) + " MB");
		}
	}
}
