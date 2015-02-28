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
	}

	public void allTests() throws IOException {
		testInputFile();
		testAdd();
		testDisplayAndClear();
		testDelete();
		testSearch();
	}
	//tests 2 cases of checkFileName function:
	//1) when there is no file i.e. create the file
	//2) when file already exists
	@Test
	public void testInputFile() throws IOException {
		//tests for creation of file
		assertEquals("Welcome to TextBuddy. test1.txt is created and ready for use \n", 
				newTest.checkFileName("test1.txt"));
		//tests for existing file
		assertEquals("Welcome to TextBuddy. test.txt is ready for use \n",
				newTest.checkFileName("test.txt"));
	}
	
	//tests add function
	@Test
	public void testAdd() throws IOException {
		assertEquals("added to test.txt: \"jumped over the moon\"\n", 
				newTest.execute("add", "add jumped over the moon"));
	}
	
	//tests clear function
	//tests 2 cases of display function:
	//1) when file is empty
	//2) when there are lines in file
	@Test
	public void testDisplayAndClear() throws IOException {
		//tests clear()
		newTest.clear();
		assertEquals("all content deleted from test.txt\n", 
				newTest.execute("clear", "clear"));
		
		//tests display() when file is empty
		assertEquals("test.txt is empty\n", 
				newTest.display());
		
		//tests display() when there are lines in file
		newTest.execute("add", "add jumped over the moon");
		newTest.execute("add", "add swam through the sea");
		assertEquals("1. jumped over the moon 2. swam through the sea ", 
				newTest.display());
	}
	
	//tests delete function
	@Test
	public void testDelete() throws IOException {
		newTest.clear();
		newTest.execute("add", "add I want to delete this");
		assertEquals("deleted from test.txt: \"I want to delete this\"\n", 
				newTest.execute("delete", "delete 1"));
	}
	
	//tests 3 cases of search function:
	//1) when file is empty
	//2) when file contains line with the keyword being searched for
	//3) when file contains line without the keyword being searched for
	@Test
	public void testSearch() {
		//when file is empty
		newTest.clear();
		assertEquals("test.txt is empty\n0 line(s) found with \"the\"\n", 
				newTest.search("search the"));
		
		//when file contains line with the keyword being searched for
		newTest.execute("add","add jumped over the moon");
		assertEquals("1. jumped over the moon\n1 line(s) found with \"the\"\n", 
				newTest.search("search the"));
		
		//when file contains line without the keyword being searched for
		assertEquals("0 line(s) found with \"sea\"\n", 
				newTest.search("search sea"));
	}

	@AfterClass
	public static void deleteAfterClass() {
		test.delete();
		File newFile = new File("test1.txt");
		newFile.delete();
	}
}
