import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;


public class TextBuddyTest {
	static TextBuddy newTest;
	static File test;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		newTest = new TextBuddy();
		test = new File("test.txt");
		test.createNewFile();
		test.deleteOnExit();
	}

	@Test
	public void testInputFile() throws IOException {
		assertEquals("Welcome to TextBuddy. test1.txt is created and ready for use \n", 
				newTest.checkFileName("test1.txt"));
		assertEquals("Welcome to TextBuddy. test.txt is ready for use \n",
				newTest.checkFileName("test.txt"));
	}
	
	@AfterClass
	public static void deleteAfterClass() {
		test.delete();
		File newFile = new File("test1.txt");
		newFile.delete();
	}
}
