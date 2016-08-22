package cooker.sample;

import java.util.ArrayList;

import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;

@CookerIngredient
public class TextCondition {

	@CookerSerializable(key="target")
	public String text;
	
	public ArrayList<String> filters;
	
	@CookerEvent(eventType=EventType.OnCook)
	public void start(){
		filters = new ArrayList<>();
		filters.add("print");
	}
	
	@CookerCondition()
	public boolean filter(){
		boolean result = false;
		for (String string : filters) {
			if(string.equalsIgnoreCase(text)){
				result = true;
				break;
			}
		}
		System.out.println("condition is " + result);
		return result;
	}
}
