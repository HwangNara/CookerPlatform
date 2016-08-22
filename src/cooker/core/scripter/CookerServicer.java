package cooker.core.scripter;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.annotations.ICookerServicer;
import cooker.core.debug.CookerException;
import cooker.core.scripter.cookings.Cooking;

public class CookerServicer extends Thread implements ICookerServicer{

	private Cooking cooking;
	private boolean firable = false;
	private boolean isRunning = false;
	
	public CookerServicer(Cooking cooking){
		this.cooking = cooking;
	}
	
	@Override
	public void start(){
		firable = false;
		isRunning = true;
		cooking.setField(ICookerServicer.class, this);
		System.out.println("start service id " + getId()+ " - " + cooking.getName() + " on " +this);
		cooking.executeEventMethod(CookerEvent.EventType.OnServiceStart);
		super.start();
	}
	
	@Override
	public void end(){
		isRunning = false;
		fire();
		cooking.executeEventMethod(CookerEvent.EventType.OnServiceEND);
		super.interrupt();
		System.out.println("end service id " + getId()+ " - " + cooking.getName() + " on " +this);
	}
	
	public synchronized void waitToBeFired() throws CookerException{
		try {
			System.out.println("Wait to be fired by trigger...");
			wait();
			System.out.println("Rerun");
		} catch (InterruptedException e) {
			throw new CookerException("%s can not wait by async trigger", cooking.getName());
		}
	}

	@Override
	public synchronized void fire(){
		System.out.println("Fire!");
		notifyAll();
	}
	
	@Override
	public void run() {
		try{
			while(isRunning){
				CookerTrigger.TriggerType triggerType = cooking.getTriggerType();
				if(triggerType == TriggerType.SYNC){
					cooking.executeTriggerMethod();
				}else if(triggerType == TriggerType.ASYNC){
					if(!firable){
						firable = true;
						cooking.executeTriggerMethod();
					}
					waitToBeFired();
				}
				if(!isRunning)
					break;
				
				cooking.executeEventMethod(CookerEvent.EventType.OnFire);
				
				cooking.wireTriggerLinks();
				if(cooking.checkCondition()){
					cooking.wireConditionLinks();
					cooking.executeActionYMethod();
				}else{
					cooking.wireConditionLinks();
					cooking.executeActionNMethod();
				}
			}
		}catch(CookerException e){
			e.printStackTrace();
		}
	}
}
