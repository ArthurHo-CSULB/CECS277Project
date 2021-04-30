package cecs277;

import java.io.File;

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
	
	public MyFileNode(String filename) {
		file = new File(filename);
		fileName = filename;
	}
	
	public MyFileNode(String name, File f) {
		file = f;
		fileName = name;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isDirectory() {
		return (file.listFiles() != null);
	}
	
	public String getFilePath() {
		return file.getAbsolutePath();
	}
	
	/**
	 *  This makes the node look pretty by parsing the absolute filepath for the last string seperated by //
	 *  
	 *  @return String the (hopefully) correct file name
	 *  
	 */
	
	public String toString() {
		String[] seperated = fileName.split("[\\\\]");
		return seperated[seperated.length-1];
	}
		
	
}
