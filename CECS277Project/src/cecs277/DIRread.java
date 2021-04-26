package cecs277;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Darius & Arthur
 *
 */
public class DIRread {
	public DIRread() {
		
	}
	/**
	 * Currently prints out a list of all directories/files on the provided path
	 * Will probably later be modified to return an array of strings or something instead of printing out the files
	 * 
	 * 
	 * @param filePath the filepath you wish to read directories from, if empty will read from "C:"
	 */
	public static void getFiles(String filePath) {
		
		if (filePath.isBlank())
			filePath = "C:\\";
		//create file to access
		File file = new File(filePath);
		// empty array of files
		File[] files;
		//populate array with files from the given filepath
		files = file.listFiles();
		

		SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat decFmt = new DecimalFormat("#,###");
		
		
		for(int i = 0; i < files.length; i++) {		
			if (files[i].isDirectory())
				System.out.println("Directory:\t"+files[i].getAbsolutePath()
								+ " Date:" + dateFmt.format(files[i].lastModified())
								+ " Size:" + decFmt.format(files[i].length()));
			else
				System.out.println("File:\t\t"+files[i].getAbsolutePath()
								+ " Date:" + dateFmt.format(files[i].lastModified())
								+ " Size:" + decFmt.format(files[i].length()));
		}
	}
	
	/**
	 * Opens file with correct filepath provided, otherwise closes the file
	 * 
	 * @param filePath filepath of file you want to open/execute MAKE SURE TO HAVE DOUBLE ESCAPE CHAR ex: \\
	 */
	public static void openFile(String filePath) {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File(filePath));
		}catch(IOException ex) {
			System.out.println(ex.toString());
		}
	}

}
