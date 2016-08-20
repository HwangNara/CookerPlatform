package cooker.core.scripter;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerTrigger;
import cooker.core.debug.CookerException;

public class Chef {
	
	public Cooking cook(RecipeTCA recipe){
		Cooking cooking = new Cooking();	
		cooking.trigger = new Ingredient<>(recipe.trigger, CookerTrigger.class);
		cooking.condition = new Ingredient<>(recipe.condition, CookerCondition.class);
		cooking.actionY = new Ingredient<>(recipe.actionY, CookerAction.class);
		cooking.actionN = new Ingredient<>(recipe.actionN, CookerAction.class);
		cooking.links.addAll(recipe.links);
		cooking.name = recipe.name;
		try {
			cooking.trigger.newInstance();
			activeLinks(cooking, cooking.trigger.instance, CookerTrigger.SYNONYM);
			cooking.condition.newInstance();
			activeLinks(cooking, cooking.condition.instance, CookerCondition.SYNONYM);
			cooking.actionY.newInstance();
			activeLinks(cooking, cooking.actionY.instance, CookerAction.SYNONYM_Y);
			cooking.actionN.newInstance();
			activeLinks(cooking, cooking.actionN.instance, CookerAction.SYNONYM_N);
			return cooking;
		} catch (CookerException e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	void activeLinks(Cooking cooking, Object target, String synonym){
		for (Link link : cooking.links) {
			if(link.from.equals(synonym))
				link.from = target;
			if(link.to.equals(synonym))
				link.to = target;
		}
	}
}
