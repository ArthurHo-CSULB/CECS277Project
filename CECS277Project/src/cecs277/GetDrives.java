package cecs277;

import java.io.File;

// This may not be necessary to have separately, it can be implemented into another class

public class GetDrives {
	public static void main(String[] args) {
		File[] drives = File.listRoots();

		for (File drive : drives) {
			System.out.println(drive);
		}
	}
}
