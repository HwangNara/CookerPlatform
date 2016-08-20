package cooker.core.scripter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import cooker.core.annotations.CookerEvent;
import cooker.core.debug.CookerException;

class Ingredient<A extends Annotation> {
	
	protected Class<?> clazz;
	protected Class<A> aClazz;
	protected Object instance;
	
	Ingredient(Class<?> clazz, Class<A> aClazz){
		this.clazz = clazz;
		this.aClazz = aClazz;
	}
	
	void newInstance() throws CookerException{ 
		try {
			this.instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CookerException("Component can not instatiate %s", clazz.getSimpleName());
		} 
	}
		
	void executeEventMethod(CookerEvent.EventType eventType){
		executeMethod(getEventMethod(eventType));
	}
	
	Object executeTCAMethod(){
		return executeMethod(getTCAMethod());
	}
	
	Object executeMethod(Method method){
		if(method == null)
			return null;
		try {
			return method.invoke(instance, new Object[]{});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	Method getEventMethod(final CookerEvent.EventType eventType){
		return AnnotationHelper.<CookerEvent>getMethod(instance, CookerEvent.class, new Predicate<CookerEvent>() {
			@Override
			public boolean test(CookerEvent t) {
				return t.eventType().equals(eventType);
			}
		});
	}
	
	Method getTCAMethod(){
		return AnnotationHelper.getMethod(instance, aClazz);
	}
	
	A getTCA(){
		return AnnotationHelper.getMethod(instance, aClazz).getDeclaredAnnotation(aClazz);
	}
}
