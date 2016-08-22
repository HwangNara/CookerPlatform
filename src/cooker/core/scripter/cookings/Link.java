package cooker.core.scripter.cookings;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import cooker.core.annotations.CookerSerializable;
import cooker.core.scripter.AnnotationHelper;

public class Link {

	public static class Node{
		public final Ingredient ingredient;
		public final String key;
		
		public Node(Ingredient ingredient, String key){
			this.ingredient = ingredient;
			this.key = key;
		}
		
		public Field getField(){
			return AnnotationHelper.getField(ingredient.instance, CookerSerializable.class, new Predicate<CookerSerializable>() {

				@Override
				public boolean test(CookerSerializable t) {
					return t.key().equals(key);
				}
			});
		}
	}
	
	public final Node from, to;
	
	public Link clone(){
		Node fClone = new Node(from.ingredient, from.key);
		Node tClone = new Node(to.ingredient, to.key);
		return new Link(fClone, tClone);
	}
	
	Link(Node from, Node to){
		this.from = from;
		this.to = to;
	}
	
	void wire(){
		try {
			Field fromField = from.getField();
			Field toField = to.getField();
			fromField.setAccessible(true);
			toField.setAccessible(true);
			Object param = fromField.get(from.ingredient.instance);
			toField.set(to.ingredient.instance, param);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
