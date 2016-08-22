package cooker.core.scripter.recipes;

import org.json.simple.JSONObject;

public class RecipeLink {
	public final RecipeNode from, to;
	
	public RecipeLink(JSONObject from, JSONObject to){
		this.from = new RecipeNode(from);
		this.to = new RecipeNode(to);
	}
	
	public RecipeLink(RecipeNode from, RecipeNode to){
		this.from = from;
		this.to = to;
	}
	
	public RecipeLink clone(){
		RecipeNode fClone = new RecipeNode(from.ingredient, from.key);
		RecipeNode tClone = new RecipeNode(to.ingredient, to.key);
		return new RecipeLink(fClone, tClone);
	}
}
