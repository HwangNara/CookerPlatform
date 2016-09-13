package cooker.sample;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerIngredient;

@CookerIngredient
public class DoNothingAction {
	
	@CookerAction
	public void DoNothing(){
		
	}
}
