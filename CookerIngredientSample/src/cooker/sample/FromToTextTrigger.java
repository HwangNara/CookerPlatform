package cooker.sample;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.scripter.service.ICookerServicer;

public class FromToTextTrigger implements KeyListener{

	ICookerServicer coookerServicer;
	
	int step = 1;
	
	@CookerSerializable(key = "from")
	String from;
	@CookerSerializable(key = "to")
	String to;
	
	TextField tf;
	Label label;
	
	@CookerEvent(eventType = EventType.OnServiceStart)
	public void openGUI(){
		
	}

	
	@CookerTrigger(triggerType = TriggerType.ASYNC)
	public void quest(){
		final Frame frame = new Frame("From-To");
		FlowLayout layout = new FlowLayout();
		frame.setLayout(layout);
		label = new Label("출발지를 입력하세요 : ");
		tf = new TextField(10);
		tf.addKeyListener(this);
		frame.add(label);
		frame.add(tf);		
		frame.setSize(400, 200);
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
				frame.dispose();
				coookerServicer.end();
		    }
		});
		frame.setVisible(true);
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(step == 1){
				from = tf.getText();
				tf.setText("");
				label.setText("목적지를 입력하세요.");				
				step++;
			}else if(step == 2){
				to = tf.getText();
				tf.setText("");
				step=1;
				label.setText("출발지를 입력하세요.");
				coookerServicer.fire();
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
