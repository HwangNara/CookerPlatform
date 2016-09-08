package cooker.editor.console;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import cooker.core.debug.CookerLogger;
import cooker.core.scripter.recipes.Cooking;
import cooker.core.scripter.recipes.chef.Chef;
import cooker.core.scripter.service.CookerServicer;

public class Console {
	private static final String TAG = Console.class.getSimpleName();
	private static Properties properties;
	private static String recipePathRoot;
	private static Cooking currentCooking;	
	private static Chef chef;
	private static Map<Long, CookerServicer> services = new HashMap<>();
	
	public static void main(String[] args){
		try{
			InputStream is = new FileInputStream("console.properties");
			properties = new Properties();
			properties.load(is);
			recipePathRoot = properties.get("recipe_path").toString();
			Scanner sc = new Scanner(System.in);
			while(true){
				String[] line = sc.nextLine().split(" ");
				String command = line[0];
				String content = line.length > 1 ? line[1] : "";
				if(command.equalsIgnoreCase("cook")){
					String recipeName = content;
					String recipePath = recipePathRoot+ recipeName +".json";
					chef = new Chef();
					currentCooking = chef.cook(recipePath);
					if(currentCooking == null){
						CookerLogger.logln(TAG, "Cook fail T.T ");
						continue;
					}else{
						CookerLogger.logln(TAG, "Cook complete! ");
					}
					continue;
				}
				
				if(command.equalsIgnoreCase("start")){
					if(currentCooking == null){
						CookerLogger.logln(TAG, "Current cooking is required. command 'cook recipe_name' first");
					}else{
						CookerServicer servicer = new CookerServicer(currentCooking);
						servicer.setDaemon(true);					
						services.put(servicer.getId(), servicer);
						servicer.start();
					}
					continue;
				}
				
				if(command.equalsIgnoreCase("end")){
					if(content.isEmpty()){
						CookerLogger.logln(TAG, "Please set service id");
						continue;
					}
					Long key = Long.parseLong(content);
					if(!services.containsKey(key)){
						CookerLogger.logln(TAG, "Nothing which has service id " + key);
						continue;
					}
					CookerServicer servicer = services.get(key);
					servicer.end();
					continue;
				}
				
				if(command.equalsIgnoreCase("exit")){
					sc.close();
					System.exit(0);
				}
				CookerLogger.logln(TAG, "Not valid command. availabes are [cook/start/end/exit]");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
