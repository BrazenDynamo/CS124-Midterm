package framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SmsParser {
	public static ArrayList<String> parseSms(String message) {
		ArrayList<String> result = new ArrayList<String>();
		
		String[] tokens = message.split(" ");
		
		for(String s : tokens) {
			result.add(s);
		}
		
		return result;
	}
}
