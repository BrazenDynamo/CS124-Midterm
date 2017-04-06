package solutions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import framework.SmsCommand;
import framework.SmsParser;

public class FrameworkDemo {
	static String username;
	static ArrayList<Method> methodList1;
	static ArrayList<String> methodNames;
	
	static HashMap<String, Method> methodList;
	// Check whether the user has STARTed the game
	static boolean started = false;
	// Default Room at game start
	public static String room = "Room1";
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		init();
		
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNext()) {
			String message = sc.nextLine();
			ArrayList<String> tokens = SmsParser.parseSms(message);
			
			String command = tokens.get(0).toUpperCase();
			String[] methodArgs = new String[tokens.size() - 1]; 
			methodArgs = tokens.toArray(methodArgs);
			
			Object[] ar = new Object[1];
			ar[0] = methodArgs;
			
			
			if(methodList.containsKey(command)) {
				System.out.println("invoking " + command + "\n");
				methodList.get(command).invoke(null, ar);
			}
			else {
				methodList.get("COMMAND").invoke(null, methodArgs);
			}
		}
	}
	
	public static void init() {
		Class<?> c = FrameworkDemo.class;
		
		Method[] methods = c.getDeclaredMethods();
		
		methodList = new HashMap<String, Method>();
		
		for(Method m : methods) {
			if(m.isAnnotationPresent(SmsCommand.class)) {
				SmsCommand a = (SmsCommand) m.getAnnotation(SmsCommand.class);
				methodList.put(a.command(), m);
			}
		}
	}
	
	@SmsCommand(command="REGISTER")
	static void register(String[] args) {
		String name = "";
		
		for(String s : args) {
			name += s + " ";
		}
		
		if (username == null) {
			username = name;
		}
		else {
			System.out.println("no");
		}
		System.out.println("Hi, " + name);
	}
	
	@SmsCommand(command = "START")
	static void start()
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else{
			started = true;
			room = "Room1";
			//System.out.println("Welcome to " + gamename);
		}
	}

	@SmsCommand(command = "GO")
	static void go(String roomSelected)
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if( started == false ){
			System.out.println("Please start the game by sending START.");
		}
		else{
			room = roomSelected;
		}
	}

	@SmsCommand(command = "HINT")
	static void hint()
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if( started == false ){
			System.out.println("Please start the game by sending START.");
		}
		else{
			// get declared methods and parameters of target class
		}
	}

	@SmsCommand(command = "COMMAND")
	static void command(String roomSelected)
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if( started == false ){
			System.out.println("Please start the game by sending START.");
		}
		else{
			// pass command to room command manager
		}
	}
}
