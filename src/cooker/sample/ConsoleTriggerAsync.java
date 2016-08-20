package cooker.sample;

import java.util.Scanner;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.annotations.CookingIngredient;
import cooker.core.scripter.TriggerWaiter;

//Cooker�� ����� Ŭ�����ΰŸ� �˷��ִ� ������̼�
@CookingIngredient
public class ConsoleTriggerAsync {

	//Serializable �Ҽ� �ִ� �ʵ� ������̼�, ������ Ű���� �������� key
	@CookerSerializable(key = "command")
	public String command;
	
	@CookerSerializable(key = "content")
	public String content;
	
	Scanner sc;
	String[] inputs;
	
	//Cooking�� �����Ҷ� ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnSTART)
	public void init(){
		sc = new Scanner(System.in);
	}
	
	//Ʈ���� �޼ҵ�
	@CookerTrigger(triggerType = TriggerType.ASYNC)
	public void waitCommand(TriggerWaiter waiter){
		inputs = sc.nextLine().split(" ");
		if(inputs.length < 2){
			waitCommand(waiter);
			return;
		}
		command = inputs[0];
		content = inputs[1];
		waiter.keepServing();
	}
	
	//Cooking�� ������ ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnEND)
	public void end(){
		if(sc != null)
			sc.close();
	}
}
