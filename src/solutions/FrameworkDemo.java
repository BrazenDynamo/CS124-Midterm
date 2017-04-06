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
	public static final String GAMENAME = "MyGame";
	
	static String username;
	static ArrayList<Method> methodList1;
	static ArrayList<String> methodNames;
	static String currentRoom;
	static boolean started;
	
	static HashMap<String, Method> methodList;
	
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
			System.out.println("Username registered.");
			System.out.println("Hello, " + name + "! " + "Welcome to " + GAMENAME + "!");
		}
		else {
			System.out.println("Name already registered. Hello, " + name + "!");
		}
	}
	
	@SmsCommand(command = "START")
	static void start()
	{
		if(username == null){
			System.out.println("Please register your username before starting the game.");
		}
		else{
			started = true;
			currentRoom = "Room1";
			
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
			currentRoom = args[1];
		}
	}
}
