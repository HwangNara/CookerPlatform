package cooker.sample;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cooker.core.annotations.CookerAction;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;

@CookerIngredient
public class DialogAction {
	
	@CookerSerializable(key="awtFrame")
	public Frame frame;
	@CookerSerializable(key="message1")
	public String text1;
	@CookerSerializable(key="message2")
	public String text2;
	
	public Dialog dialog;
	
	@CookerAction
	public void show(){		
		dialog = new Dialog(frame);
		dialog.setSize(200, 100);
		dialog.add(new Label(text1 + " " + text2));		
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
				dialog.dispose();		
		    }
		});
	}
}
