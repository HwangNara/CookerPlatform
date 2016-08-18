package cooker.core.scripter;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.debug.CookerException;

public class Cook {

	Component<CookerTrigger> trigger;
	Component<CookerCondition> condition;
	Component<CookerAction> actionY, actionN;
	
	RecipeTCA recipe;
	
	
	public void cooking(RecipeTCA recipe){
			init(recipe);
			trigger.executeEventMethod(CookerEvent.EventType.OnSTART);
			condition.executeEventMethod(CookerEvent.EventType.OnSTART);
			actionY.executeEventMethod(CookerEvent.EventType.OnSTART);
			actionN.executeEventMethod(CookerEvent.EventType.OnSTART);
			if(fireTrigger()){
				executeLinks(getTargets(trigger.clone));
				if(checkCondition()){
					executeLinks(getTargets(condition.clone));
					doActionY();
				}else{
					executeLinks(getTargets(condition.clone));
					doActionN();
				}
			}
			
			trigger.executeEventMethod(CookerEvent.EventType.OnEND);
			condition.executeEventMethod(CookerEvent.EventType.OnEND);
			actionY.executeEventMethod(CookerEvent.EventType.OnEND);
			actionN.executeEventMethod(CookerEvent.EventType.OnEND);
			
	}
	
	void init(RecipeTCA recipe){
		this.trigger = recipe.trigger;
		this.condition = recipe.condition;
		this.actionY = recipe.actionY;
		this.actionN = recipe.actionN;
		this.recipe = recipe;
		try {
			trigger.newInstance();
			activeLink(trigger.clone, CookerTrigger.SYNONYM);
			condition.newInstance();
			activeLink(condition.clone, CookerCondition.SYNONYM);
			actionY.newInstance();
			activeLink(actionY.clone, CookerAction.SYNONYM_Y);
			actionN.newInstance();
			activeLink(actionN.clone, CookerAction.SYNONYM_N);
		} catch (CookerException e) {
			e.printStackTrace();
		}
	}
	
	void activeLink(Object target, String synonym){
		for (Link link : recipe.links) {
			if(link.from.equals(synonym))
				link.from = target;
			if(link.to.equals(synonym))
				link.to = target;
		}
	}
	
	boolean fireTrigger(){
		CookerTrigger.TriggerType triggerType = trigger.getTCA().triggerType();
		if(triggerType == TriggerType.ONCE){
			boolean fired = (boolean)trigger.executeTCAMethod();
			return fired;
		}else if(triggerType == TriggerType.EVENT){
			return false;
		}
		return false;
	}
	
	boolean checkCondition(){
		boolean test = (boolean)condition.executeTCAMethod();
		return test;
	}
	
	void doActionY(){
		actionY.executeTCAMethod();
	}
	
	void doActionN(){
		actionN.executeTCAMethod();
	}
	
	Stream<Link> getTargets(final Object from){
		return recipe.links.stream().filter(new Predicate<Link>() {
			@Override
			public boolean test(Link link) {
				return link.from.equals(from);
			}
		});
	}
	
	void executeLinks(Stream<Link> targets){
		targets.forEach(new Consumer<Link>() {
			@Override
			public void accept(Link link) {
				link.execute();
			}
		});
	}
}
