package cooker.sample;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.annotations.ICookerServicer;

//Cooker를 사용할 클래스인거를 알려주는 어노테이션
@CookerIngredient(name = "PlusExamGUI")
public class ButtonTrigger{
	
	//ICookerServicer will be auto wired by Cooker  
	ICookerServicer coookerServicer;
	Button buttonGenerate;
	Label label1;
	Label label2;
	TextField textfield;
	Button buttonSubmit;
	
	//This filed is Serializable. Key is used in recipe links.
	@CookerSerializable(key = "awtFrame")
	Frame frame;
	@CookerSerializable(key = "value1")
	Integer value1;
	@CookerSerializable(key = "value2")
	Integer value2;
	@CookerSerializable(key = "answer")
	Integer answer;
		
	//OnCookEvent is called once per cooking(similar with instantiating).
	@CookerEvent(eventType = EventType.OnCook)
	public void init(){
		
	}
	
	//OnServiceStartEvent is called once per starting service.
	//Difference between OnCookEvent is thread dependency.
	//One cooking can be shared to multiple service.
	@CookerEvent(eventType = EventType.OnServiceStart)
	public void start(){
		openGUIWindow();
		generate();
	}

	//Asynchronized trigger method is called once after service start.
	//Synchronized trigger method is called per every routine by trigger.
	//Async trigger should call cookerServicer.fire() manually.
	//You can call cookerServicer.end() manually also. 
	@CookerTrigger(triggerType = TriggerType.ASYNC)
	public void mainTrigger(){
		buttonGenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generate();
				
			}
		});
		buttonSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					answer = Integer.parseInt(textfield.getText());	
					coookerServicer.fire();
				}catch (Exception ex) {
					
				}
			}
		});
		frame.addWindowListener(new WindowAdapter(){
			@Override
		    public void windowClosing(WindowEvent e)
		    {
				coookerServicer.end();				
		    }
		});
	}
	
	public void openGUIWindow(){
		frame = new Frame("Button Trigger Sample");
		FlowLayout layout = new FlowLayout();		
		label1 = new Label();
		label2 = new Label();
		textfield = new TextField(10);		
		buttonGenerate = new Button("generate");
		buttonSubmit = new Button("submit");
		frame.setLayout(layout);
		frame.add(label1);
		frame.add(new Label("+"));
		frame.add(label2);
		frame.add(textfield);
		frame.add(buttonGenerate);
		frame.add(buttonSubmit);
		frame.setSize(600, 300);
		frame.setVisible(true);		
	}
	
	private void generate(){
		Random r = new Random();
		value1 = r.nextInt(10);
		value2 = r.nextInt(10);
		label1.setText(String.valueOf(value1));
		label2.setText(String.valueOf(value2));
	}
	
	//OnServiceEndEvent is called once when service end.
	@CookerEvent(eventType = EventType.OnServiceEND)
	public void end(){
		frame.dispose();
	}
}
