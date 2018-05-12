/** Class containing JUnit tests, written to check functionality correctness  */

package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class Tests {

	@Test
	public void testOpeningFile() { // test if a known file opens correctly and returns an expected file name
		File file = new File("Organek - Kate Moss.mp3");
		String fileName = new String();
		try {
			if (file != null)
				fileName = file.toString();
		} catch(Exception e) {
			System.out.println("Error in test 'opening file' - " + e.toString());
		}
	assertEquals(fileName, "Organek - Kate Moss.mp3"); // assuming there is such file in the project folder!!!
	}

	@Test
	public void testID3TagRead() { // test if a known file with a working ID3 tag returns an expected title
		File file = new File("Organek - Kate Moss.mp3");
		String title = new String();
		try {		
			int size = (int)file.length();
			InputStream in = new FileInputStream(file);
			in.skip(size - 128);
			byte[] last128 = new byte[128];
			in.read(last128);
			String id3 = new String(last128);
			title = id3.substring(3, 12); // the 'artist' part of the tag, shortened so that it just contains the actual title 
			in.close();
		} catch(Exception e) {
			System.out.println("Error in test 'ID3TagRead' - " + e.toString());
		}
		System.out.print(title);
	assertEquals(title, "Kate Moss"); // assuming there is such file in the project folder!!!
	}

	
	
}
