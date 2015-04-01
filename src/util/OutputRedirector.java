package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class OutputRedirector {
	
	/**
	 * Redirect the output of System.out and System.err to two files.
	 * @param sysOut The name of the System.out output file.
	 * @param sysErr The name of the System.err output file.
	 */
	public static void redirectOutput(String dir, String sysOut, String sysErr) {
		try {
			File dirFile = new File(dir);
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}
			
			File sysErrFile = new File(dir + "/" + sysErr);
			if(!sysErrFile.exists()) {
				sysErrFile.createNewFile();
			}
			
			File sysOutFile = new File(dir + "/" + sysOut);
			if(!sysOutFile.exists()) {
				sysOutFile.createNewFile();
			}
			
			System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(sysErrFile))));
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(sysOutFile))));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
