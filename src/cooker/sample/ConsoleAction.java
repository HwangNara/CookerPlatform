package cooker.sample;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookingIngredient;

@CookingIngredient
public class ConsoleAction {

	@CookerSerializable(key = "text")
	public String text;
	
	@CookerAction
	public void print(){
		System.out.println("Print : " + text);
	}
}
