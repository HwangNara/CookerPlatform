package cooker.sample;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;

@CookerIngredient
public class ConsoleAction {

	@CookerSerializable(key = "text")
	public String text;
	
	@CookerAction
	public void print(){
		System.out.println("Print : " + text);
	}
}
