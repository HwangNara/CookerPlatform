package cooker.core.scripter.recipes.chef;

import java.lang.reflect.Field;

import cooker.core.annotations.CookerSerializable;
import cooker.core.debug.CookerLogger;
import cooker.core.scripter.AnnotationHelper;
import cooker.core.scripter.CookerPredicate;
import cooker.core.scripter.service.CookerServicer;

public class Link {

	public static class Node{
		public final Ingredient ingredient;
		public final String key;
		
		public Node(Ingredient ingredient, String key){
			this.ingredient = ingredient;
			this.key = key;
		}
		
		public Field getField(){
			return AnnotationHelper.getField(ingredient.instance, CookerSerializable.class, new CookerPredicate<CookerSerializable>() {

				@Override
				public boolean test(CookerSerializable t) {
					return t.key().equals(key);
				}
			});
		}
	}
	
	public final Node from, to;
	
	public Link(Node from, Node to){
		this.from = from;
		this.to = to;
	}
	
	public void wire(){
		try {
			Field fromField = from.getField();
			Field toField = to.getField();
			if(fromField == null){
				CookerLogger.logln(CookerServicer.TAG, "Wiring is ignored - There is no mathched field %s in %s", from.key, from.ingredient.synonym);
				return;
			}
			if(toField == null){
				CookerLogger.logln(CookerServicer.TAG, "Wiring is ignored - There is no mathched field %s in %s", to.key, to.ingredient.synonym);
				return;
			}
			fromField.setAccessible(true);
			toField.setAccessible(true);
			
			Object param = fromField.get(from.ingredient.instance);
			toField.set(to.ingredient.instance, param);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			CookerLogger.logln(CookerServicer.TAG, "Can not wire from %s(%s) to %s(%s)", from.ingredient.synonym, from.key, to.ingredient.synonym, to.key);
		}
	}
}
