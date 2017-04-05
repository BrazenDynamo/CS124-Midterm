package solutions;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class Hint {
		
	public void execute(String command, String room, int state) throws ClassNotFoundException {
		//System.out.println(rcm.processRoom("Room2", 0, "checkRoom"));
		//FastClasspathScanner scanner = new FastClasspathScanner(room);
		Class<?> c = Class.forName(room);
		Method[] methods = c.getDeclaredMethods();
		if(methods.length > 0)
		{		
			System.out.println("Methods: ");
			for(Method method : methods)
			{
				Class<?>[] parameters = method.getParameterTypes();
				String params = "";
				for(int i = 0; i < parameters.length; i++)
				{
					if(i < parameters.length - 1)
					{
						params += parameters[i]+", ";
					}
					else
					{
					params += parameters[i];
					}
				}
				System.out.println("\t"+Modifier.toString(method.getModifiers())+" "+method.getReturnType()+" "+method.getName()+"("+params+");");				     
			}
			System.out.println("");
		}
	}
}


