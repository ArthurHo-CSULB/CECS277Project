package cecs277;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Node class to keep track of all the different nodes in the file and tree
 * Add helper methods as needed
 * 
 * @author Darius & Arthur
 *
 */
public class MyFileNode {
	private File file;
	private String fileName;
	private String rootDrive;
	
	public MyFileNode(String filename) {
		file = new File(filename);
		fileName = filename;
		String[] seperated = getFileName().split("[\\\\]");
		rootDrive = seperated[0];
	}
	
	public MyFileNode(String name, File f) {
		file = f;
		fileName = name;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getRootDrive() {
		return rootDrive;
	}
	
	public boolean isDirectory() {
		return (file.isDirectory());
	}
	
	public String getFilePath() {
		return file.getAbsolutePath();
	}
	
	public String getFileName() {
		return fileName;
	}
	
	/**
	 *  This makes the node look pretty by parsing the absolute filepath for the last string seperated by //
	 *  
	 *  @return String the (hopefully) correct file name
	 *  
	 */
	
	public String toString() {
		String[] seperated = getFileName().split("[\\\\]");
		return seperated[seperated.length-1];
	}
		
	
}
