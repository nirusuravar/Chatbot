package com;

// Import all Java packages including scanner and Alicebot files
import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import java.io.*;
import java.util.Scanner;
import java.util.*;

//Constructor
public class Chatbot {
	private static final boolean TRACE_MODE = false;
	static String botName = "super"; // Name of robot is Super 
	 HashMap<String, String> knowledge = new HashMap<String, String>();

// MAIN METHOD
	public static void main(String[] args) { // Declare main method
		try {
			
			System.out.println("Hello I'm Super! and you are?"); // Super starts off the conversation
			Scanner in = new Scanner(System.in); // Sets the user input to variable in
	        String namer = in.nextLine(); // New String namer 


	        namer.trim(); // Trim method 
	        if (namer.length()>15){ System.out.println("Your name is kinda long isn't it!"); }
	        // if the length of the user's name is greater than 15 characters, print that 
	        // LENGTH METHOD + DISTINCT STRING METHOD


			String resourcesPath = getResourcesPath(); // Initialize chatbot process
			System.out.println(resourcesPath); // print out the resources path 
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath); // new robot super (reset)
			Chat chatSession = new Chat(bot); // new chatbot, new chat session
			bot.brain.nodeStats();
			String textLine = "";

			// ITERATION
			while(true) { // while true....
				System.out.print("Human : "); // print 'Human: '
				textLine = IOUtils.readInputTextLine(); // next user input 
				if ((textLine == null) || (textLine.length() < 1)) // if the length is less that one...
					textLine = MagicStrings.null_input; // no input
				if (textLine.equals("q")) { // if the user input is q
					System.exit(0); // quit the program
				} else if (textLine.equals("wq")) { // if the user input is wq
					bot.writeQuit(); // bot stops responding
					System.exit(0); // quit the program
				} else {
					String request = textLine; // if the tetx is > 1
					if (MagicBooleans.trace_mode) // saves chat history (line below)
						System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
					String response = chatSession.multisentenceRespond(request); // multi-sentence response
					while (response.contains("&lt;")) // replace &lt
						response = response.replace("&lt;", "<"); // with <, REPLACE METHOD = DISTINCT STRING METHOD
					while (response.contains("&gt;")) // replace &gt
						response = response.replace("&gt;", ">"); // with ?
					System.out.println("Robot : " + response); // print robot's response
				}
			}
		} catch (Exception e) { // if there is an error
			e.printStackTrace(); // trace the error 
		}
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}


/**
* @param question This is user input as string
*/
public void answer(String question) {
    Set<String> keys = knowledge.keySet();
    for (String key : keys){
        String lowerKey = key.toLowerCase();
        String lowerQuestion = question.toLowerCase();
        if (lowerKey.contains(lowerQuestion)) {
           System.out.println("Bot: " + knowledge.get(key));
           trainMe(question);
           return;//break
       
        }
    }
}
// train method allows for the bot to understand how user's would understand
public void trainMe(String question) {
    System.out.println("Bot: Sorry, I'm dumb! How should I reply");
    System.out.print("User suggestion: ");
    Scanner sc = new Scanner(System.in);
    String userInput = sc.nextLine();
    knowledge.put(question, userInput);
}

}
