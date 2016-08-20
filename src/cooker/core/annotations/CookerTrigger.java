package cooker.core.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CookerTrigger{
	final String SYNONYM = "trigger";
	enum TriggerType{SYNC, ASYNC}
	TriggerType triggerType();
}
