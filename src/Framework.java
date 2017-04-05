import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import solutions.Solution;

public class Framework {
	// field that contains current room
	// field for name
	// field for state (binary switches)
	static Scanner sc = new Scanner(System.in);
	static String message = "";
	static HashMap<String, Solution> solutionMap = new HashMap<String, Solution>();
	static String room = "Room1";
	public static int state = 0;
	
	// constructor
	// assign solutions to hashmap
	// ("Start", Start.class)
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// initialize map
		ScanResult results = new FastClasspathScanner("solutions").scan();		
		List<String> allResults = results.getNamesOfClassesImplementing(Solution.class);
		// System.out.println(allResults);
		for (String s : allResults)
		{
			Class<?> c = Class.forName(s);
			solutionMap.put(s.toLowerCase(), (Solution) c.newInstance());
		}
		
		// start game
		System.out.println("Welcome to MyGame!\n");
		System.out.println("Please enter REGISTER <name> to register your name, then enter START to begin your adventure!");

		// deal with command
		while(sc.hasNext()) {
			message = sc.nextLine();
			String command = getCommand(message).trim().toLowerCase();
			switch(command){
			case "register":
				break;
			case "start":
				break;
			case "go":
				break;
			case "hint":
				break;
			default:
				//Command
				break;	
			}
			
			solutionMap.get("solutions."+command).execute(message, room, state);
		}
	}
	
	private static String getCommand(String message) {
		String result = "";
		for(int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			if(c == ' ') {
				break;
			}
			else {
				result += c;
			}
		}
		
		return result;
	}

}
