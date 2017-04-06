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
}
