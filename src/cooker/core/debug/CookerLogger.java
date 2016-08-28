package cooker.core.debug;

public class CookerLogger{

	public static void logln(String message){
		System.out.println(message);
	}
	
	public static void logln(String tag, String message){
		System.out.println(String.format("[%s]%s", tag, message));
	}
	
	public static void logln(String format, Object... args){
		System.out.println(String.format(format, args));
	}
	
	public static void logln(String tag, String format, Object... args){
		System.out.println(String.format("[%s]%s", tag, String.format(format, args)));
	}
}
