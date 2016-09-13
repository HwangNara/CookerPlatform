package cooker.core.scripter.recipes.chef;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cooker.core.annotations.CookerEvent;
import cooker.core.debug.CookerException;
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
	
	private IRecipeReader recipeReader;
	private ICustomClassLoader customClassLoader;
	
	public Chef setCustomClassLoader(ICustomClassLoader customClassLoader){
		this.customClassLoader = customClassLoader;
		return this;
	}
	
	public void readRecipe(InputStream is, Charset encoding){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
			
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null){
				sb.append(line);
				sb.append('\n');
			}
			String entireString = sb.toString();
			recipeReader = new JsonRecipeReader();
			recipeReader.init(entireString);			
			CookerLogger.logln("Succeed to read recipe!");			
		}catch(IOException | CookerException e){
			CookerLogger.logln("Failed reading recipe");
		}
	}
	
	public void readRecipe(File file, Charset encoding){
		byte[] encoded;
		Path path = file.toPath();
		try {
			encoded = Files.readAllBytes(path);
			String str = new String(encoded, encoding);
			recipeReader = new JsonRecipeReader();
			recipeReader.init(str);
			CookerLogger.logln("Succeed to read recipe!");
		} catch (IOException | CookerException e) {
			CookerLogger.logln("%s is not vaild path", path);
		}
	}

	
	public Cooking cook(){
		try{
			// 4. create empty cooking
			RecipeType recipeType = recipeReader.getRecipeType();
			Cooking cooking = newCooking(recipeType);
			
			// 3. add CookerURLs
			List<CookerURL> cookerURLs = new ArrayList<>();
			String[] ingredientKeys = Ingredient_Keys[recipeType.ordinal()];
			for (int i = 0; i < ingredientKeys.length; i++) {
				String key = ingredientKeys[i];
				CookerURL cookerURL = recipeReader.getURL(key);
				cookerURLs.add(cookerURL);
			}
						
			//4. create class loader
			ClassLoader classLoader = null;
			if(customClassLoader != null){
				classLoader = this.customClassLoader.getClassLoader(cookerURLs);
			}else{
				URL[] urls = new URL[cookerURLs.size()];
				for (int i = 0; i < urls.length; i++) {
					urls[i] = cookerURLs.get(i).toURL();
				}
				classLoader = new URLClassLoader(urls);
			}
			
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
			for (Pair<String, String> linkPair : recipeReader.getLinkPairs()) {
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
			cooking.executeEventMethod(CookerEvent.EventType.OnCook);
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
