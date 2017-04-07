package solutions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import framework.SmsCommand;
import framework.SmsParser;
import room.RoomCommandManager;

public class FrameworkDemo {
	public static final String GAMENAME = "MyGame";
	
	static ArrayList<Method> methodList1;
	static ArrayList<String> methodNames;
	
	static HashMap<String, Method> methodList;
	static RoomCommandManager rcm;
	
	static String username;
	static int gameState;
	static String currentRoom;
	static boolean started;
	
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
				methodList.get(command).invoke(null, ar);
			}
			else {
				methodList.get("COMMAND").invoke(null, (Object)methodArgs);
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
		
		rcm = new RoomCommandManager();
		
		gameState = 0;
	}
	
	@SmsCommand(command="REGISTER")
	static void register(String[] args) {
		String name = "";
		String[] nameArr = Arrays.copyOfRange(args, 1, args.length);
		for(String s : nameArr) {
			name += s + " ";
		}
		
		if (username == null && name.length() > 0) {
			username = name;
			System.out.println("Username registered.");
			System.out.println("Hello, " + username + "! " + "Welcome to " + GAMENAME + "!");
		}
		else if (name.length() == 0)
		{
			System.out.println("Please enter a valid name");
		}
		else
		{
			System.out.println("Name already registered. Hello, " + username + "!");
		}
	}
	
	@SmsCommand(command = "START")
	static void start(String[] args)
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if(started == false)
		{
			started = true;
			currentRoom = "Room1";
			gameState = (Integer) rcm.processRoom(currentRoom, gameState, "checkRoom").get("status");
			System.out.println(rcm.processRoom(currentRoom, gameState, "checkRoom").get("message"));
		}
		else
		{
			System.out.println("The game has already started, " + username + ". Please try a different command.");
			// System.out.println("CONFIRM RESTART. ALL PROGRESS WILL BE RESET. Y/N?");
			
		}
	}
	
	@SmsCommand(command = "GO")
	static void go(String[] args)
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if( started == false ){
			System.out.println("Please start the game by sending START.");
		}
		else{
			currentRoom = args[1].substring(0, 1).toUpperCase() + args[1].substring(1).toLowerCase()  ;
			HashMap<String, Object> result = rcm.processRoom(currentRoom, gameState, "checkRoom");
			System.out.println(result.get("message"));
			gameState = (int) result.get("status");
		}
	}
	
	@SmsCommand(command="HINT")
	static void hint(String[] args) throws ClassNotFoundException {
		if(started) {
//			Class<?> c = Class.forName("room." + currentRoom);
//		
//			Method[] methods = c.getDeclaredMethods();
//			
//			System.out.println("For a moment, you ponder what you can do here. You realize that you can:");
//			for(Method m : methods) {
//				System.out.println(m.getName());
//			}
//			
//			System.out.println("");
			HashMap<String, Object> result = rcm.processRoom(currentRoom, gameState, "checkRoom");
			System.out.println(result.get("message"));
			gameState = (int) result.get("status");
		}
		else {
			System.out.println("You haven't started the game!");
		}
	}
	
	@SmsCommand(command = "COMMAND")
	static void command(String[] args)
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else if( started == false ){
			System.out.println("Please start the game by sending START.");
		}
		else{
			try {
				String joinedString = String.join(" ", args);
				HashMap<String, Object> result = rcm.processRoom(currentRoom, gameState, joinedString);
				System.out.println(result.get("message"));
				gameState = (int) result.get("status");
				// System.out.println((Integer) rcm.processRoom(currentRoom, gameState, joinedString).get("status"));
			}
			catch (RuntimeException e) {
				if(e.getMessage().substring(0, 2).equals("No")) {
					System.out.println("You can't do that.");
				}
			}
		}
	}
}