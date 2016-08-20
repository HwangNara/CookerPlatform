package cooker.sample;

import java.util.Scanner;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.annotations.CookingIngredient;

//Cooker�� ����� Ŭ�����ΰŸ� �˷��ִ� ������̼�
@CookingIngredient
public class ConsoleTriggerSync {

	//Serializable �Ҽ� �ִ� �ʵ� ������̼�, ������ Ű���� �������� key
	@CookerSerializable(key = "command")
	public String command;
	
	@CookerSerializable(key = "content")
	public String content;
	
	Scanner sc;
	
	//Cooking�� �����Ҷ� ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnSTART)
	public void init(){
		sc = new Scanner(System.in);
	}
	
	//Ʈ���� �޼ҵ�
	@CookerTrigger(triggerType = TriggerType.SYNC)
	public boolean fire(){
		String[] texts = sc.nextLine().split(" ");
		if(texts.length < 2)
			return false;
		command = texts[0];
		content = texts[1];
		return true;
	}	
	
	//Cooking�� ������ ����Ǵ� �̺�Ʈ �޼ҵ�
	@CookerEvent(eventType = EventType.OnEND)
	public void end(){
		if(sc != null)
			sc.close();
	}
}
