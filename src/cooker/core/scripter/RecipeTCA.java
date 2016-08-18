package cooker.core.scripter;

import java.util.ArrayList;
import java.util.List;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerTrigger;

public class RecipeTCA {
	
	public static class Builder{
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
		
		public Builder addLink(Link link){
			links.add(link);
			return this;
		}
		
		public RecipeTCA build(){
			RecipeTCA r = new RecipeTCA(trigger, condition, actionY, actionN);
			r.links = links;
			return r;
		}
	}
	
	Component<CookerTrigger> trigger;
	Component<CookerCondition> condition;
	Component<CookerAction> actionY;
	Component<CookerAction> actionN;
	List<Link> links = new ArrayList<>();
	
	RecipeTCA(Class<? extends Object> trigger, Class<? extends Object> condition, Class<? extends Object> actionY, Class<? extends Object> actionN){
		this.trigger = new Component<>(trigger, CookerTrigger.class);
		this.condition = new Component<>(condition, CookerCondition.class);
		this.actionY = new Component<>(actionY, CookerAction.class);
		this.actionN = new Component<>(actionN, CookerAction.class);
	}
}
