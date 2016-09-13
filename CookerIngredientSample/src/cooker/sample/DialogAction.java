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
	
	@CookerSerializable(key="message1")
	public String text1 = "this is dialog message1";
	@CookerSerializable(key="message2")
	public String text2 = "this is dialog message2";
	@CookerSerializable(key="awtFrame")
	public Frame frame;
	public Dialog dialog;
	
	@CookerAction
	public void show(){		
		newFrame();
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
	
	private void newFrame(){
		if(frame != null)
			return;
		frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
				frame.dispose();		
		    }
		});
		frame.setVisible(true);
	}
}
