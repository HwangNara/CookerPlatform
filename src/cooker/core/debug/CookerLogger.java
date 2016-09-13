package cooker.core.debug;

import java.io.PrintStream;

import android.util.Log;
import cooker.core.scripter.recipes.Restaurant;

public class CookerLogger{

	public static PrintStream out = System.out; 
	
	public static void logln(String message){
		if(Restaurant.isAndroid()){
			Log.i("cooker", message);
		}else{
			out.println(message);
		}
	}
	
	public static void logln(String tag, String message){
		if(Restaurant.isAndroid()){
			Log.i(tag, message);
		}else{
			out.println(String.format("[%s]%s", tag, message));
		}
	}
	
	public static void logln(String format, Object... args){
		String message = String.format(format, args);
		if(Restaurant.isAndroid()){
			Log.i("cooker", message);
		}else{
			out.println(message);
		}
	}
	
	public static void logln(String tag, String format, Object... args){
		String message = String.format(format, args);
		if(Restaurant.isAndroid()){
			Log.i(tag, message);
		}else{
			out.println(String.format("[%s]%s", tag, message));
		}
	}
}
