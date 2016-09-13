package cooker.sample;

import java.util.Scanner;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.scripter.service.ICookerServicer;

//Cooker�� ����� Ŭ�����ΰŸ� �˷��ִ� ������̼�
@CookerIngredient
public class ConsoleTrigger {

	//Serializable �Ҽ� �ִ� �ʵ� ������̼�, ������ Ű���� �������� key
	@CookerSerializable(key = "command")
	public String command;
	
	@CookerSerializable(key = "content")
	public String content;
	
	Scanner sc;
	String[] inputs;
	ICookerServicer servicer;
	
	//Cooking�� �����Ҷ� ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnServiceStart)
	public void init(){
		sc = new Scanner(System.in);
	}
	
	//Ʈ���� �޼ҵ�
	@CookerTrigger(triggerType = TriggerType.SYNC)
	public void waitCommand(){
		inputs = sc.nextLine().split(" ");
		command = inputs[0];
		content = inputs.length > 1 ? inputs[1] : "";
		if(command.equalsIgnoreCase("end"))
			servicer.end();
	}	
	
	//Cooking�� ������ ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnServiceEND)
	public void end(){
		if(sc != null)
			sc.close();
	}
}
