package cooker.core.scripter.recipes;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerEvent;
import cooker.core.scripter.service.CookerServicer;
import cooker.core.scripter.service.ICookerServicer;

public class SimpleCooking extends Cooking {

	@Override
	public void onStart(CookerServicer servicer) {
		super.setField(ICookerServicer.class, servicer);		
		super.executeEventMethod(CookerEvent.EventType.OnServiceStart);		
		ingredients.get("main").executeMethod(CookerAction.class);
	}

	@Override
	public boolean isWait(CookerServicer servicer) {
		return false;
	}

	@Override
	public void onWait(CookerServicer servicer) {
		
	}

	@Override
	public void onRerun(CookerServicer servicer) {
		servicer.end();
	}

	@Override
	public void onEnd(CookerServicer servicer) {
		
	}
}
