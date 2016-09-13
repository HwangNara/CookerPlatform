package cooker.sample;

import java.util.Scanner;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.scripter.service.ICookerServicer;

//Cooker를 사용할 클래스인거를 알려주는 어노테이션
@CookerIngredient
public class ConsoleTrigger {

	//Serializable 할수 있는 필드 어노테이션, 여기의 키값이 레시피의 key
	@CookerSerializable(key = "command")
	public String command;
	
	@CookerSerializable(key = "content")
	public String content;
	
	Scanner sc;
	String[] inputs;
	ICookerServicer servicer;
	
	//Cooking이 시작할때 실행되는 이벤트 메소드
	@CookerEvent(eventType = EventType.OnServiceStart)
	public void init(){
		sc = new Scanner(System.in);
	}
	
	//트리거 메소드
	@CookerTrigger(triggerType = TriggerType.SYNC)
	public void waitCommand(){
		inputs = sc.nextLine().split(" ");
		command = inputs[0];
		content = inputs.length > 1 ? inputs[1] : "";
		if(command.equalsIgnoreCase("end"))
			servicer.end();
	}	
	
	//Cooking이 끝나면 실행되는 이벤트 메소드
	@CookerEvent(eventType = EventType.OnServiceEND)
	public void end(){
		if(sc != null)
			sc.close();
	}
}
