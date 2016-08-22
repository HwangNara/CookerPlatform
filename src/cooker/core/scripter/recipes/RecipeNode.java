package cooker.core.scripter.recipes;

import org.json.simple.JSONObject;

public class RecipeNode {
	public final String ingredient;
	public final String key;
	
	public RecipeNode(JSONObject jsonObj){
		this.ingredient = jsonObj.get("ingredient").toString();
		this.key = jsonObj.get("key").toString();
	}
	
	public RecipeNode(String ingredient, String key){
		this.ingredient = ingredient;
		this.key = key;
	}
}
