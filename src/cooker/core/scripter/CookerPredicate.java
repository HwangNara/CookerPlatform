package cooker.core.scripter;

import java.lang.annotation.Annotation;

public interface CookerPredicate<A extends Annotation> {

	boolean test(A a);
}
