package cooker.editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import cooker.core.scripter.CookerServicer;
import cooker.core.scripter.cookings.Chef;
import cooker.core.scripter.cookings.Cooking;
import cooker.core.scripter.recipes.TCARecipe;

public class ConsoleEditor {
	
	private static Properties properties;
	private static String recipePathRoot;
	private static TCARecipe currentRecipe;
	private static Cooking currentCooking;	
	private static Chef chef;
	private static Map<Long, CookerServicer> services = new HashMap<>();
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
       
		InputStream is = new FileInputStream("cooker-1.0/console.properties");
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
				JSONReader jr = new JSONReader();
				String recipePath = recipePathRoot+ recipeName +".json";
				currentRecipe = jr.readFromFile(recipePath);
				if(currentRecipe == null){
					println("cook fail - " + recipePath);
					continue;
				}
				chef = new Chef();
				currentCooking = chef.cook(currentRecipe);
				if(currentCooking == null){
					println("cook fail - " + recipePath);
				}else{
					println("cook complete! - " + recipePath);
				}
				continue;
			}
			
			if(command.equalsIgnoreCase("start")){
				if(currentCooking == null){
					println("current cooking is required. command 'cook recipe_name' first");
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
					println("please set service id");
					continue;
				}
				Long key = Long.parseLong(content);
				if(!services.containsKey(key)){
					System.out.println("nothing which has service id " + key);
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
			println("not valid command. availabes are [cook/start/end/exit]");
		}
	}
	
	
	private static void println(Object obj){
		System.out.println("[Editor] " + obj.toString());
	}
	
}
