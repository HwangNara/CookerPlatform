package cooker.core.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CookerAction {
	final String SYNONYM = "action";
	final String SYNONYM_Y = "actionY";
	final String SYNONYM_N = "actionN";
}
