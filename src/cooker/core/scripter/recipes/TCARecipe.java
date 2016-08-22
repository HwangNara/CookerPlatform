package cooker.core.scripter.recipes;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class TCARecipe {
	
	public static class Builder{
		private String name;
		private Class<?> trigger;
		private Class<?> condition;
		private Class<?> actionY;
		private Class<?> actionN;
		private List<RecipeLink> links = new ArrayList<>();
		
		public Builder(Class<?> trigger, Class<?> condition, Class<?> actionY, Class<?> actionN){
			this.trigger = trigger;
			this.condition = condition;
			this.actionY = actionY;
			this.actionN = actionN;
		}
		
		public Builder setName(String name){
			this.name = name;
			return this;
		}
		
		public Builder addLink(JSONObject from, JSONObject to){
			links.add(new RecipeLink(from, to));
			return this;
		}
		
		public TCARecipe build(){
			return new TCARecipe(this);
		}
	}
	
	public final String name;
	public final Class<?> trigger;
	public final Class<?> condition;
	public final Class<?> actionY;
	public final Class<?> actionN;
	public final List<RecipeLink> links = new ArrayList<>();
	
	private TCARecipe(Builder builder){
		this.name = builder.name;
		this.trigger = builder.trigger;
		this.condition = builder.condition;
		this.actionY = builder.actionY;
		this.actionN = builder.actionN;
		for (RecipeLink link : builder.links) {
			this.links.add(link.clone());
		}
	}
}