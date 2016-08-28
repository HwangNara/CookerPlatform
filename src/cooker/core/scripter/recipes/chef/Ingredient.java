package cooker.core.scripter.recipes.chef;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import cooker.core.annotations.CookerEvent;
import cooker.core.debug.CookerException;
import cooker.core.scripter.AnnotationHelper;

public class Ingredient{
	
	protected Class<?> clazz;
	protected String synonym;
	protected Object instance;
	
	public Ingredient(Class<?> clazz, String synonym){
		this.clazz = clazz;
		this.synonym = synonym;
	}
	
	public void newInstance() throws CookerException{ 
		try {
			this.instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CookerException("%s can not instatiate %s", this.getClass().getSimpleName(), clazz.getSimpleName());
		} 
	}
	
	public void setField(Class<?> clazz, Object value){
		for(Field field : instance.getClass().getDeclaredFields()){
			if(field.getType().getSimpleName().equals(clazz.getSimpleName())){
				field.setAccessible(true);
				try {
					field.set(instance, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
		
	public Object executeEventMethod(final CookerEvent.EventType eventType){
		Method method = AnnotationHelper.<CookerEvent>getMethod(instance, CookerEvent.class, new Predicate<CookerEvent>() {
			@Override
			public boolean test(CookerEvent t) {
				return t.eventType().equals(eventType);
			}
		});
		return executeMethod(method);
	}
	
	public Object executeMethod(Class<? extends Annotation> aClazz){
		Method method = AnnotationHelper.getMethod(instance, aClazz);
		return executeMethod(method);
	}
	
	public Object executeMethod(Method method){
		if(method == null)
			return null;
		try {
			return method.invoke(instance, new Object[]{});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	public <A extends Annotation> A getMethodAnnotation(Class<A> aClazz){
		return AnnotationHelper.getMethod(instance, aClazz).getDeclaredAnnotation(aClazz);
	}
}
