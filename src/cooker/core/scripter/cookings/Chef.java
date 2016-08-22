package cooker.core.scripter.cookings;

import java.util.ArrayList;
import java.util.List;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.debug.CookerException;
import cooker.core.scripter.cookings.Link.Node;
import cooker.core.scripter.recipes.RecipeLink;
import cooker.core.scripter.recipes.TCARecipe;

public class Chef {
	
	protected List<Ingredient> ingredients = new ArrayList<>();
	
	public Cooking cook(TCARecipe recipe){
		Ingredient trigger = new Ingredient(recipe.trigger, CookerTrigger.SYNONYM);
		Ingredient condition = new Ingredient(recipe.condition, CookerCondition.SYNONYM);
		Ingredient actionY = new Ingredient(recipe.actionY, CookerAction.SYNONYM_Y);
		Ingredient actionN = new Ingredient(recipe.actionN, CookerAction.SYNONYM_N);
		ingredients.add(trigger);
		ingredients.add(condition);
		ingredients.add(actionY);
		ingredients.add(actionN);
		Cooking.Builder builder = new Cooking.Builder(trigger, condition, actionY, actionN);
		builder.setName(recipe.name);
		for (RecipeLink recipeLink : recipe.links) {
			Ingredient fromIngredient = convert(recipeLink.from.ingredient);
			Ingredient toIngredient = convert(recipeLink.to.ingredient);
			String fromKey = recipeLink.from.key;
			String toKey = recipeLink.to.key;
			Node from = new Node(fromIngredient, fromKey);
			Node to = new Node(toIngredient, toKey);
			Link link = new Link(from, to);
			builder.addLink(link);
		}
		try {
			Cooking cooking = builder.Build();
			cooking.newInstance();
			cooking.executeEventMethod(EventType.OnCook);
			return cooking;
		} catch (CookerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Ingredient convert(String str){
		for (Ingredient ingredient : ingredients) {
			if(ingredient.synonym.equals(str))
				return ingredient;
		}
		return null;
	}
}
