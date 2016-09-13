package cooker.sample.android;

import java.lang.reflect.InvocationTargetException;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;
import cooker.core.scripter.service.ICookerServicer;

@CookerIngredient
public class ToastAction {

	@CookerSerializable(key = "text")
	public String text = "Hello Cooker!";
	
	@CookerSerializable(key = "context")
	public Context context;
	
	public ICookerServicer servicer;
	
	@CookerAction
	public void print(){
		context = context == null ? getApplicationUsingReflection() : context;
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		servicer.end();
	}
	
	public static Application getApplicationUsingReflection() {
		Application ret = null;
	    try {
			ret = (Application) Class.forName("android.app.ActivityThread")
			        .getMethod("currentApplication").invoke(null, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	    return ret;
	}
}

