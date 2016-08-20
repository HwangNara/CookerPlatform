package cooker.core.scripter;

import java.util.ArrayList;
import java.util.List;

public class RecipeTCA {
	
	public static class Builder{
		private String name;
		private Class<?> trigger;
		private Class<?> condition;
		private Class<?> actionY;
		private Class<?> actionN;
		private List<Link> links = new ArrayList<>();
		
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
		
		public Builder addLink(Link link){
			links.add(link);
			return this;
		}
		
		public RecipeTCA build(){
			RecipeTCA r = new RecipeTCA(trigger, condition, actionY, actionN);
			r.links = links;
			r.name = name;
			return r;
		}
	}
	
	String name;
	Class<?> trigger;
	Class<?> condition;
	Class<?> actionY;
	Class<?> actionN;
	List<Link> links = new ArrayList<>();
	
	private RecipeTCA(Class<? extends Object> trigger, Class<? extends Object> condition, Class<? extends Object> actionY, Class<? extends Object> actionN){
		this.trigger = trigger;
		this.condition = condition;
		this.actionY = actionY;
		this.actionN = actionN;
	}
}
