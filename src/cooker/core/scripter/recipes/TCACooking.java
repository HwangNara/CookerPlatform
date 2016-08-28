package cooker.core.scripter.recipes;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.scripter.recipes.chef.Ingredient;
import cooker.core.scripter.service.CookerServicer;
import cooker.core.scripter.service.ICookerServicer;

public class TCACooking extends Cooking{
	
	private Ingredient trigger;
	private Ingredient condition;
	private Ingredient actionY;
	private Ingredient actionN;
	private TriggerType triggerType;

	@Override
	public void onStart(CookerServicer servicer) {
		this.trigger = ingredients.get(CookerTrigger.SYNONYM);
		this.condition = ingredients.get(CookerCondition.SYNONYM);
		this.actionY = ingredients.get(CookerAction.SYNONYM_Y);
		this.actionN = ingredients.get(CookerAction.SYNONYM_N);
		this.triggerType = trigger.getMethodAnnotation(CookerTrigger.class).triggerType();
		super.setField(ICookerServicer.class, servicer);
		super.executeEventMethod(CookerEvent.EventType.OnServiceStart);
		if(triggerType == TriggerType.ASYNC){
			executeTriggerMethod();
		}
	}
	
	@Override
	public boolean isWait(CookerServicer servicer){
		if(triggerType == TriggerType.SYNC){
			executeTriggerMethod();
			return false;
		}else if(triggerType == TriggerType.ASYNC){
			return true;
		}else
			return true;
	}
	
	@Override
	public void onWait(CookerServicer servicer) {

	}

	@Override
	public void onRerun(CookerServicer servicer) {
		super.executeEventMethod(CookerEvent.EventType.OnFire);
		wireTriggerLinks();
		if(checkCondition()){
			wireConditionLinks();
			executeActionYMethod();
			wireActionYLinks();
		}else{
			wireConditionLinks();
			executeActionNMethod();
			wireActionNLinks();
		}
	}

	@Override
	public void onEnd(CookerServicer servicer) {
		super.executeEventMethod(CookerEvent.EventType.OnServiceEND);
	}
	
	private void executeTriggerMethod(){
		trigger.executeMethod(CookerTrigger.class);
	}
	
	private boolean checkCondition(){
		return (boolean)condition.executeMethod(CookerCondition.class);
	}
	
	private void executeActionYMethod(){
		actionY.executeMethod(CookerAction.class);
	}
	
	private void executeActionNMethod(){
		actionN.executeMethod(CookerAction.class);
	}
	
	private void wireTriggerLinks(){
		wireLinks(trigger);
	}
	
	private void wireConditionLinks(){
		wireLinks(condition);
	}
	
	private void wireActionYLinks(){
		wireLinks(actionY);
	}
	
	private void wireActionNLinks(){
		wireLinks(actionN);
	}
}

