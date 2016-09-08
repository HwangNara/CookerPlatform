package cooker.core.scripter.recipes.chef;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.debug.CookerLogger;
import cooker.core.scripter.recipes.Cooking;
import cooker.core.scripter.recipes.SimpleCooking;
import cooker.core.scripter.recipes.TCACooking;
import cooker.core.scripter.recipes.chef.Link.Node;

public class Chef {

	public static final String TAG = Chef.class.getSimpleName();
	
	public enum RecipeType{Simple, TCA};
	public static final String[][] Ingredient_Keys = new String[][]{
		{"main"},
		{"trigger", "condition", "actionY", "actionN"}
	};
	
	public Cooking cook(String filePath){
		try{
			//1. create new file
			File file = new File(filePath);
			
			//2. find file extension. 
			int pos = filePath.lastIndexOf( "." );
			String extension = filePath.substring( pos + 1 );
			
			//3. create extension assistant
			IAssistantable assistant = null;
			if(extension.equalsIgnoreCase("json")){
				assistant = new JsonAssistant();
				assistant.init(file);
			}
			
			// 4. create empty cooking
			RecipeType recipeType = assistant.getRecipeType();
			Cooking cooking = newCooking(recipeType);
			
			// 3. add CookerURLs
			List<CookerURL> cookerURLs = new ArrayList<>();
			String[] ingredientKeys = Ingredient_Keys[recipeType.ordinal()];
			for (int i = 0; i < ingredientKeys.length; i++) {
				String key = ingredientKeys[i];
				CookerURL cookerURL = assistant.getURL(key);
				cookerURLs.add(cookerURL);
			}
						
			//4. create class loader
			URL[] urls = new URL[cookerURLs.size()];  
			for (int i = 0; i < urls.length; i++) {
				urls[i] = cookerURLs.get(i).toURL();
			}
			@SuppressWarnings("resource")
			ClassLoader classLoader = new URLClassLoader(urls);
			
			//5. create ingredients and put into cooking
			for (CookerURL cookerURL : cookerURLs) {
				Class<?> clazz = classLoader.loadClass(cookerURL.className);
				CookerLogger.logln(TAG, cookerURL.getFullURI());
				Ingredient ingredient = new Ingredient(clazz, cookerURL.key);
				cooking.ingredients.put(cookerURL.key, ingredient);
			}
						
			//6. create links and add into cooking
			int i = 1;
			Node from = null, to = null;			
			for (Pair<String, String> linkPair : assistant.getLinkPairs()) {
				String ingredientKey = linkPair.getElement0();
				String serializableKey = linkPair.getElement1();
				Ingredient ingredient = cooking.ingredients.get(ingredientKey);
				if(i % 2 == 1){
					from = new Node(ingredient, serializableKey); 
				}else{
					to = new Node(ingredient, serializableKey);
					cooking.links.add(new Link(from, to));
				}
				i++;
			}
			
			//7. create new instances of cooking ingredients
			cooking.newInstance();
			
			//8. execute on cook event
			cooking.executeEventMethod(EventType.OnCook);
			return cooking;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	protected static Cooking newCooking(RecipeType type){
		Cooking recipe = null;
		if(type == RecipeType.Simple){
			recipe = new SimpleCooking();
		}else if(type == RecipeType.TCA){
			recipe = new TCACooking();
		}
		return recipe;
	}
}
