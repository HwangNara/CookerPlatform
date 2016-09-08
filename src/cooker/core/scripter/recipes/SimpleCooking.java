package cooker.core.scripter.recipes;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.scripter.service.CookerServicer;

public class SimpleCooking extends Cooking {

	@Override
	public void onStart(CookerServicer servicer) {
		super.executeEventMethod(EventType.OnServiceStart);
		ingredients.get("main").executeMethod(CookerAction.class);
	}

	@Override
	public boolean isWait(CookerServicer servicer) {
		return true;
	}

	@Override
	public void onWait(CookerServicer servicer) {
		
		
	}

	@Override
	public void onRerun(CookerServicer servicer) {
		super.executeEventMethod(EventType.OnFire);
		
	}

	@Override
	public void onEnd(CookerServicer servicer) {
		super.executeEventMethod(EventType.OnServiceEND);
		
	}


}
