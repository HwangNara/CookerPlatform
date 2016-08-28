package cooker.core.scripter.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cooker.core.annotations.CookerEvent;
import cooker.core.debug.CookerException;
import cooker.core.scripter.recipes.chef.Ingredient;
import cooker.core.scripter.recipes.chef.Link;
import cooker.core.scripter.service.ICookerServiceListener;

public abstract class Cooking implements ICookerServiceListener{
	public final String name = "unknown";
	public final Map<String, Ingredient> ingredients = new HashMap<>();
	public final List<Link> links = new ArrayList<>();

	public void setField(Class<?> clazz, Object target){
		for (Ingredient ingredient : ingredients.values()) {
			ingredient.setField(clazz, target);
		} 
	}
	
	public void executeEventMethod(CookerEvent.EventType eventType){
		for (Ingredient ingredient : ingredients.values()) {
			ingredient.executeEventMethod(eventType);
		} 
	}
	
	public void newInstance() throws CookerException{
		for (Ingredient ingredient : ingredients.values()) {
			ingredient.newInstance();
		} 
	}
	
	protected void wireLinks(Ingredient from){
		for (Link link : links) {
			if(link.from.ingredient.equals(from)){
				link.wire();
			}
		}
	}
}
