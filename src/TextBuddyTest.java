import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;


public class TextBuddyTest {
	static TextBuddy newTest;
	static File test;
	
	//sets up the test
	//initial empty file named "test.txt"
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		newTest = new TextBuddy();
		test = new File("test.txt");
		test.createNewFile();
		test.deleteOnExit();
	}

	public void allTests() throws IOException {
		testInputFile();
		testAdd();
		testDisplayAndClear();
		//testDelete();
	}
	//tests for creation of new file
	@Test
	public void testInputFile() throws IOException {
		//tests for creation of file
		assertEquals("Welcome to TextBuddy. test1.txt is created and ready for use \n", 
				newTest.checkFileName("test1.txt"));
		//tests for existing file
		assertEquals("Welcome to TextBuddy. test.txt is ready for use \n",
				newTest.checkFileName("test.txt"));
	}
	
	//tests if lines are added to the file correctly
	@Test
	public void testAdd() throws IOException {
		assertEquals("added to test.txt: \"jumped over the moon\"\n", 
				newTest.execute("add", "add jumped over the moon"));
	}
	
	//tests display and clear function
	@Test
	public void testDisplayAndClear() throws IOException {
		//tests display() when there are lines in file
		newTest.execute("add", "add swam through the sea");
		assertEquals("1. jumped over the moon 2. swam through the sea ", 
				newTest.display());
		
		//tests clear()
		newTest.clear();
		assertEquals("all content deleted from test.txt\n", 
				newTest.execute("clear", "clear"));
		
		//tests display() when file is empty
		assertEquals("test.txt is empty\n", 
				newTest.display());
	}
	
//	@Test
//	public void testDelete() throws IOException {
//		newTest.execute("add", "add swam through the sea");
//		assertEquals("1. jumped over the moon 2. swam through the sea ", 
//				newTest.display());
//	}
	
	@AfterClass
	public static void deleteAfterClass() {
		File newFile = new File("test1.txt");
		newFile.delete();
	}
}
