package cecs277;

import java.io.File;

public class GetDrives {
	public static void main(String[] args) {
		File[] drives = File.listRoots();

		for (File drive : drives) {
			System.out.println(drive);
		}
	}
}
