package cooker.core.scripter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationHelper {
	
	public static Method getMethod(Object object,  Class<? extends Annotation> aClazz){
		for (Method method : object.getClass().getDeclaredMethods()) {
			Annotation annotation = getDeclaredAnnotation(method, aClazz);
			if(annotation != null){
				return method;
			}
		} 
		return null;
	}
	
	public static <A extends Annotation> Method getMethod(Object object, Class<A> aClazz, CookerPredicate<A> predicate){
		for (Method method : object.getClass().getDeclaredMethods()) {
			Annotation annotation = getDeclaredAnnotation(method, aClazz, predicate);
			if(annotation != null){
				return method;
			}
		} 
		return null;
	}
	
	public static List<Method> getMethods(Object object,  Class<? extends Annotation> aClazz){
		List<Method> list = new ArrayList<>();
		for (Method method : object.getClass().getDeclaredMethods()) {
			Annotation annotation = getDeclaredAnnotation(method, aClazz);
			if(annotation != null){
				list.add(method);
			}
		} 
		return list;
	}
	
	public static <A extends Annotation> List<Method> getMethods(Object object, Class<A> aClazz, CookerPredicate<A> predicate){
		List<Method> list = new ArrayList<>();
		for (Method method : object.getClass().getDeclaredMethods()) {
			Annotation annotation = getDeclaredAnnotation(method, aClazz, predicate);
			if(annotation != null){
				list.add(method);
			}
		} 
		return list;
	}
	
	public static Field getField(Object object, Class<? extends Annotation> aClazz){
		for (Field field : object.getClass().getDeclaredFields()) {
			Annotation annotation = getDeclaredAnnotation(field, aClazz);
			if(annotation != null){
				return field;
			}
		} 
		return null;
	}
	
	public static <A extends Annotation> Field getField(Object object, Class<A> aClazz, CookerPredicate<A> predicate){
		for (Field field : object.getClass().getDeclaredFields()) {
			Annotation annotation = getDeclaredAnnotation(field, aClazz, predicate);
			if(annotation != null){
				return field;
			}
		} 
		return null;
	}
	
	public static List<Field> getFields(Object object, Class<? extends Annotation> aClazz){
		List<Field> list = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) {
			Annotation annotation = getDeclaredAnnotation(field, aClazz);
			if(annotation != null){
				list.add(field);
			}
		} 
		return list;
	}
	
	public static <A extends Annotation> List<Field> getFields(Object object, Class<A> aClazz, CookerPredicate<A> predicate){
		List<Field> list = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) {			
			Annotation annotation = getDeclaredAnnotation(field, aClazz, predicate);
			if(annotation != null){
				list.add(field);
			}
		} 
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> A getDeclaredAnnotation(Method method, Class<A> aClazz){
		for (Annotation annotation : method.getDeclaredAnnotations()){
			if(annotation.annotationType().equals(aClazz)){
				return (A)annotation;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> A getDeclaredAnnotation(Method method, Class<A> aClazz, CookerPredicate<A> predicate){
		for (Annotation annotation : method.getDeclaredAnnotations()){
			if(annotation.annotationType().equals(aClazz)){
				A a = (A)annotation; 
				if(predicate.test(a)){
					return a;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> A getDeclaredAnnotation(Field field, Class<A> aClazz){
		for (Annotation annotation : field.getDeclaredAnnotations()){
			if(annotation.annotationType().equals(aClazz)){
				return (A)annotation;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> A getDeclaredAnnotation(Field field, Class<A> aClazz, CookerPredicate<A> predicate){
		for (Annotation annotation : field.getDeclaredAnnotations()){
			if(annotation.annotationType().equals(aClazz)){
				A a = (A)annotation; 
				if(predicate.test(a)){
					return a;
				}
			}
		}
		return null;
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
