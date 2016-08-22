package cooker.core.scripter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AnnotationHelper {
	
	public static Method getMethod(Object object,  Class<? extends Annotation> aClazz){
		for (Method method : object.getClass().getDeclaredMethods()) {
			if(method.getDeclaredAnnotation(aClazz) == null)
				continue;
			return method;
		} 
		return null;
	}
	
	public static <A extends Annotation> Method getMethod(Object object, Class<A> aClazz, Predicate<A> predicate){
		for (Method method : object.getClass().getDeclaredMethods()) {
			A a = method.getDeclaredAnnotation(aClazz);
			if(a == null)
				continue;
			if(predicate.test(a))
				return method;
		} 
		return null;
	}
	
	public static List<Method> getMethods(Object object,  Class<? extends Annotation> aClazz){
		List<Method> list = new ArrayList<>();
		for (Method method : object.getClass().getDeclaredMethods()) {
			if(method.getDeclaredAnnotation(aClazz) == null)
				continue;
			list.add(method);
		} 
		return list;
	}
	
	public static <A extends Annotation> List<Method> getMethods(Object object, Class<A> aClazz, Predicate<A> predicate){
		List<Method> list = new ArrayList<>();
		for (Method method : object.getClass().getDeclaredMethods()) {
			A a = method.getDeclaredAnnotation(aClazz);
			if(a == null || !predicate.test(a))
				continue;
			list.add(method);
		} 
		return list;
	}
	
	public static Field getField(Object object, Class<? extends Annotation> aClazz){
		for (Field field : object.getClass().getDeclaredFields()) {
			if(field.getDeclaredAnnotation(aClazz) == null)
				continue;
			return field;
		} 
		return null;
	}
	
	public static <A extends Annotation> Field getField(Object object, Class<A> aClazz, Predicate<A> predicate){
		for (Field field : object.getClass().getDeclaredFields()) {
			A a = field.getDeclaredAnnotation(aClazz);
			if(a == null)
				continue;
			if(predicate.test(a))
				return field;
		} 
		return null;
	}
	
	public static List<Field> getFields(Object object, Class<? extends Annotation> aClazz){
		List<Field> list = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) {
			if(field.getDeclaredAnnotation(aClazz) == null)
				continue;
			list.add(field);
		} 
		return list;
	}
	
	public static <A extends Annotation> List<Field> getFields(Object object, Class<A> aClazz, Predicate<A> predicate){
		List<Field> list = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) {
			A a = field.getDeclaredAnnotation(aClazz);
			if(a == null || !predicate.test(a))
				continue;
			list.add(field);
		} 
		return list;
	}
	

	/*
	
	
	public static <A extends Annotation> Method getMethod(Object object, final Class<? extends Annotation> aClazz, final Predicate<A> predicate){
		Stream<Method> stream = AnnotationHelper.getMethods(object, aClazz).stream();
		Optional<Method> method = stream.filter(new Predicate<Method>() {
			@Override
			public boolean test(Method t) {
				@SuppressWarnings("unchecked")
				A a = (A)t.getDeclaredAnnotation(aClazz);
				return predicate.test(a);
			}
		}).findFirst();
		return method.isPresent() ? method.get() : null;
	}
	
	public static <A extends Annotation> Field getField(Object object, final Class<? extends Annotation> aClazz, final Predicate<A> predicate){
		Stream<Field> stream = AnnotationHelper.getFields(object, aClazz).stream();
		Optional<Field> field = stream.filter(new Predicate<Field>() {
			@Override
			public boolean test(Field t) {
				@SuppressWarnings("unchecked")
				A a = (A)t.getDeclaredAnnotation(aClazz);
				return predicate.test(a);
			}
		}).findFirst();
		return field.isPresent() ? field.get() : null;
	}
	*/
	/*
	@SuppressWarnings("unchecked")
	public static <A> Field[] getFields(Object object, Class<? extends Annotation> aClazz, Predicate<A> predicate){
		Stream<Field> stream = AnnotationHelper.getFields(object, aClazz).stream();
		return stream.filter((f) -> predicate.test((A)f.getDeclaredAnnotation(aClazz))).toArray((size) -> new Field[size]);
	}*/


}
