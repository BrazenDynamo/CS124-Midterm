package solutions;

public class Register implements Solution {
	public static String name;
	private final int COMMAND_LENGTH = 8; // length of 'register '
	
	public void execute(String command, String room, int state) {
		if(name == null) {
			name = command.substring(COMMAND_LENGTH + 1);
			System.out.println("Hello, " + name + "! Your name has been registered.");
		}
		else {
			System.out.println("I already know your name. It's " + name + "!");
		}
	}
}
