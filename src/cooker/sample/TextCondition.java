package cooker.sample;

import java.util.ArrayList;

import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookingIngredient;

@CookingIngredient
public class TextCondition {

	@CookerSerializable(key="target")
	public String text;
	
	public ArrayList<String> filters;
	
	@CookerEvent(eventType=EventType.OnSTART)
	public void start(){
		filters = new ArrayList<>();
		filters.add("print");
	}
	
	@CookerCondition()
	public boolean filter(){
		for (String string : filters) {
			if(string.equals(text))
				return true;
		}
		return false;
	}
}
