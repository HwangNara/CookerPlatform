package cooker.core.scripter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.debug.CookerException;

public class Cooking implements Runnable{

	String name = "unknown";
	Ingredient<CookerTrigger> trigger;
	Ingredient<CookerCondition> condition;
	Ingredient<CookerAction> actionY, actionN;
	
	List<Link> links = new ArrayList<>();
	boolean isServed;
	
	@Override
	public void run() {
		try{
			trigger.executeEventMethod(CookerEvent.EventType.OnSTART);
			condition.executeEventMethod(CookerEvent.EventType.OnSTART);
			actionY.executeEventMethod(CookerEvent.EventType.OnSTART);
			actionN.executeEventMethod(CookerEvent.EventType.OnSTART);
			if(fireTrigger()){
				executeLinks(getTargets(trigger.instance));
				if(checkCondition()){
					executeLinks(getTargets(condition.instance));
					doActionY();
				}else{
					executeLinks(getTargets(condition.instance));
					doActionN();
				}
			}
			
			trigger.executeEventMethod(CookerEvent.EventType.OnEND);
			condition.executeEventMethod(CookerEvent.EventType.OnEND);
			actionY.executeEventMethod(CookerEvent.EventType.OnEND);
			actionN.executeEventMethod(CookerEvent.EventType.OnEND);
		}catch(CookerException e){
			e.printStackTrace();
		}
	}
	
	boolean fireTrigger() throws CookerException{
		CookerTrigger.TriggerType triggerType = trigger.getTCA().triggerType();
		if(triggerType == TriggerType.SYNC){
			boolean fired = (boolean)trigger.executeTCAMethod();
			return fired;
		}else if(triggerType == TriggerType.ASYNC){
			trigger.executeTCAMethod();
			waitUntilServing();
			return true;
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
		return links.stream().filter(new Predicate<Link>() {
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
	
	synchronized void waitUntilServing() throws CookerException{
		try {
			while(!isServed){
				wait();
			}
			isServed = false;
			System.out.println("Wait until serving...");
		} catch (InterruptedException e) {
			throw new CookerException("%s can not wait by async trigger", name);
		}
	}
	
	synchronized void keepServing(){
		isServed = true;
		notifyAll();
		System.out.println("Keep serving!");
	}
}
