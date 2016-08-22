package cooker.core.scripter.cookings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerTrigger;
import cooker.core.debug.CookerException;

public class Cooking{

	public static class Builder{
		private String name;
		private Ingredient trigger;
		private Ingredient condition;
		private Ingredient actionY, actionN;
		private List<Link> links = new ArrayList<>();
		
		public Builder(Ingredient trigger, Ingredient condition, Ingredient actionY, Ingredient actionN){
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
			this.links.add(link);
			return this;
		}
		
		public Builder addLinks(Iterable<Link> iterable){
			Iterator<Link> iter = iterable.iterator();
			while(iter.hasNext()){
				links.add(iter.next());
			}
			return this;
		}
		
		public Cooking Build(){
			return new Cooking(this);
		}
	}
	
	private String name = "unknown";
	public final Ingredient trigger;
	public final Ingredient condition;
	public final Ingredient actionY, actionN;
	public final List<Link> links = new ArrayList<>();
	
	private Cooking(Builder builder){
		this.name = builder.name;
		this.trigger = builder.trigger;
		this.condition = builder.condition;
		this.actionY = builder.actionY;
		this.actionN = builder.actionN;
		for (Link link : builder.links) {
			links.add(link.clone());
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setField(Class<?> clazz, Object target){
		trigger.setField(clazz, target);
		condition.setField(clazz, target);
		actionY.setField(clazz, target);
		actionN.setField(clazz, target);
	}
	
	public void executeEventMethod(CookerEvent.EventType eventType){
		trigger.executeEventMethod(eventType);
		condition.executeEventMethod(eventType);
		actionY.executeEventMethod(eventType);
		actionN.executeEventMethod(eventType);
	}
	
	public void executeTriggerMethod(){
		trigger.executeMethod(CookerTrigger.class);
	}
	
	public CookerTrigger.TriggerType getTriggerType(){
		return trigger.getMethodAnnotation(CookerTrigger.class).triggerType();
	}
	
	public boolean checkCondition(){
		return (boolean)condition.executeMethod(CookerCondition.class);
	}
	
	public void executeActionYMethod(){
		actionY.executeMethod(CookerAction.class);
	}
	
	public void executeActionNMethod(){
		actionN.executeMethod(CookerAction.class);
	}
	
	public void wireTriggerLinks(){
		wireLinks(trigger);
	}
	
	public void wireConditionLinks(){
		wireLinks(condition);
	}
	
	public void wireActionYLinks(){
		wireLinks(actionY);
	}
	
	public void wireActionNLinks(){
		wireLinks(actionN);
	}
	
	void newInstance() throws CookerException{
		trigger.newInstance();
		condition.newInstance();
		actionY.newInstance();
		actionN.newInstance();
	}
	
	private void wireLinks(Ingredient from){
		for (Link link : links) {
			if(link.from.ingredient.equals(from)){
				link.wire();
			}
		}
	}
}
