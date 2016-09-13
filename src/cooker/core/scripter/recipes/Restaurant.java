package cooker.core.scripter.recipes;

import android.app.Activity;

public class Restaurant {
	
	public static String os = System.getProperty("os.name");
	public static Activity activity;
	
	public static void initAndroid(Activity activity){
		Restaurant.activity = activity;
	}
	
	public static boolean isWindows(){
		return os.startsWith("Windows");
	}
	
	public static boolean isAndroid(){
		return os.startsWith("Android");
	}	
}
