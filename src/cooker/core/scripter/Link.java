package cooker.core.scripter;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import cooker.core.annotations.CookerSerializable;

public class Link {

	public Object from;
	public Object to;
	
	public String fromKey;
	public String toKey;
	
	public Link(Object from, String fromKey, Object to, String toKey){
		this.from = from;
		this.to = to;
		this.fromKey = fromKey;
		this.toKey = toKey;
	}
	
	void execute(){
		try {
			Field fromField = getField(from, fromKey);
			Field toField = getField(to, toKey);
			fromField.setAccessible(true);
			toField.setAccessible(true);
			Object param = fromField.get(from);
			toField.set(to, param);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Field getField(Object target, final String key){
		return AnnotationHelper.<CookerSerializable>getField(target, CookerSerializable.class, new Predicate<CookerSerializable>() {

			@Override
			public boolean test(CookerSerializable t) {
				return t.key().equals(key);
			}
		});
	}
}
