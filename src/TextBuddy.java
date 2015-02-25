import java.io.*;
import java.util.*;

//Assume file to manipulate exists
//Saves file after each time file is edited


public class TextBuddy {
	private static String inputFileName = "";
	private static String tempFileName = "temp.txt";
	
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use \n";
	private static final String NO_INPUT_FILE_MESSAGE = "No file input. Please input a file.";
	private static final String ENTER_COMMAND_MESSAGE = "command: ";
	private static final String WRONG_COMMAND_MESSAGE = "Wrong command detected. Please check your input\n";
	private static final String ADDED_MESSAGE = "added to %1$s: \"%2$s\"\n";
	private static final String DISPLAY_MESSAGE = "%1$s. %2$s\n";
	private static final String EMPTY_MESSAGE = "%1$s is empty\n";
	private static final String DELETED_MESSAGE = "deleted from %1$s: \"%2$s\"\n";
	private static final String CANNOT_DELETE_MESSAGE = "Line does not exist in %1$s";
	private static final String CLEARED_MESSAGE = "all content deleted from %1$s\n";
	
	
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		initialise(args);
		run();
	}
	
	//---------------------------------------- initialise() METHODS ---------------------------------------//
	
	//checks input file exists, and informs the user TextBuddy is ready for use
	public static void initialise(String[] args) throws IOException {
		prepInputFile(args);
		showToUser(formatMessage(WELCOME_MESSAGE, inputFileName));
	}

	//checks if there is file input, ensures the input file exists, saving file name if it does
	private static void prepInputFile(String[] args) throws IOException {
		if (args.length < 1) {
			showToUser(NO_INPUT_FILE_MESSAGE);
			exit();
		} else {
			String inputFileNameTemp = args[0];
			checkFileName(inputFileNameTemp);
		}
	}

	//sets inputFileName if the file exists, and informs the user file is ready
	private static void checkFileName(String inputFileNameTemp) throws IOException {
		File newFile = new File(inputFileNameTemp);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		
		inputFileName = inputFileNameTemp;
	}
	//---------------------------------------- END OF initialise() METHODS -------------------------------//
	
	//---------------------------------------- run() METHODS ---------------------------------------------//
	
	//handles input given by user once file is ascertained.
	private static void run() {
		while (true) {
			showToUser(ENTER_COMMAND_MESSAGE);
			String userInput = scanner.nextLine();
			inputHandler(userInput);
		}
	}
	
	//extracts and checks command in user input, executing input if correct
	private static void inputHandler(String userInput) {
		String command = getCommand(userInput);
		showToUser(execute(command, userInput));
	}

	//takes in user input and returns the command if any
	//ensures commands entered are in lower case, handling any CAPS problems for command entry
	private static String getCommand(String userInput) {
		String command = userInput.trim().split("\\s+")[0];
		command = command.toLowerCase();
		return command;
	}
	
	//executes commands given by user
	public static String execute(String command, String userInput) {
		String message = "";
		switch(command) {
		case "add":
			message = add(userInput);
			break;
		case "display":
			display();
			break;
		case "delete":
			message = delete(userInput);
			break;
		case "clear":
			message = clear();
			break;
		case "exit":
			exit();
			break;
		default:
			message = WRONG_COMMAND_MESSAGE;
			break;
		}
		return message;
	}
	
	//adds the desired line into the file
	private static String add(String userInput) {
		String inputToAdd = getInput(userInput);
		String message = "";
		try {
			FileWriter fw = new FileWriter(inputFileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			 
			bw.write(inputToAdd + "\n");
			message = formatMessage(ADDED_MESSAGE, inputFileName, inputToAdd);
			bw.close();

			
		 } catch (IOException e) {
			 showToUser(e.getMessage());
		 }
		return message;
	}
	
	//displays the current text in file
	private static void display() {
		int count = 1;
		File displayFile = new File(inputFileName);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(displayFile);
		} catch (FileNotFoundException e) {
			showToUser(e.getMessage());
		}
		
		if (!fileScanner.hasNextLine()) {
			showToUser(formatMessage(EMPTY_MESSAGE, inputFileName));
		}
			
		while (fileScanner.hasNextLine()) {
			showToUser(formatMessage(DISPLAY_MESSAGE, count + "", fileScanner.nextLine()));
			count++;
		}
		
		fileScanner.close();
	}

	//deletes indicated line from file by copying other lines into a new file, and replacing the original file with the new file
	private static String delete(String userInput) {
		String message = "";
		int indexToDelete = Integer.parseInt(getInput(userInput));
		
		try {
			File inputFile = new File(inputFileName);
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			
			File tempFile = new File(tempFileName);
			FileWriter fw = new FileWriter(tempFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String str = br.readLine();
			String removedLine = null;
			int count = 1;
			while (str != null) {
				if (count == indexToDelete) {
					removedLine = str;
				} else {
					bw.write(str);
					bw.write("\n");
				}
				
				str = br.readLine();
				count++;
			}
			

			br.close();
			bw.close();
			
			inputFile.delete();
			tempFile.renameTo(inputFile);
			
			if (removedLine == null) {
				message = CANNOT_DELETE_MESSAGE;
			} else {
				message = formatMessage(DELETED_MESSAGE, inputFileName, removedLine);
			}
		} catch (Exception e) {
			showToUser(e.getMessage());
		}
		
		return message;
	}

	//clears all content from file
	private static String clear() {
		PrintWriter pw = null;
		String message = "";
		try {
			pw = new PrintWriter(inputFileName);
			pw.close();
			
			message = formatMessage(CLEARED_MESSAGE, inputFileName);
			
		} catch (FileNotFoundException e) {
			message = e.getMessage();
		}
		return message;
	}
	
	//gets the input aside from the command itself
	private static String getInput(String userInput) {
		return userInput.replace(getCommand(userInput), "").trim();
	}
	//---------------------------------------- END OF run() METHODS ----------------------------------------//
	
	//---------------------------------------- GENERAL METHODS ---------------------------------------------//

	//given a text, print the text to show to user
	private static void showToUser(String text) {
		System.out.print(text);
	}

	//given a message format and text, returns the formatted message with added text
	private static String formatMessage(String message, String text) {
		return String.format(message, text);
	}
	
	private static String formatMessage(String message, String text1, String text2) {
		return String.format(message, text1, text2);
	}
	
	//exit TextBuddy
	private static void exit() {
		System.exit(0);
	}
	//-------------------------------------- END OF GENERAL METHODS ----------------------------------------//
}
