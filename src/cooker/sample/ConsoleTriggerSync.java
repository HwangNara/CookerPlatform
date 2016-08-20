package cooker.sample;

import java.util.Scanner;

import cooker.core.annotations.CookerEvent;
import cooker.core.annotations.CookerEvent.EventType;
import cooker.core.annotations.CookerSerializable;
import cooker.core.annotations.CookerTrigger;
import cooker.core.annotations.CookerTrigger.TriggerType;
import cooker.core.annotations.CookingIngredient;

//Cooker를 사용할 클래스인거를 알려주는 어노테이션
@CookingIngredient
public class ConsoleTriggerSync {

	//Serializable 할수 있는 필드 어노테이션, 여기의 키값이 레시피의 key
	@CookerSerializable(key = "command")
	public String command;
	
	@CookerSerializable(key = "content")
	public String content;
	
	Scanner sc;
	
	//Cooking이 시작할때 실행되는 이벤트 메소드
	@CookerEvent(eventType = EventType.OnSTART)
	public void init(){
		sc = new Scanner(System.in);
	}
	
	//트리거 메소드
	@CookerTrigger(triggerType = TriggerType.SYNC)
	public boolean fire(){
		String[] texts = sc.nextLine().split(" ");
		if(texts.length < 2)
			return false;
		command = texts[0];
		content = texts[1];
		return true;
	}	
	
	//Cooking이 끝나면 실행되는 이벤트 메소드
	@CookerEvent(eventType = EventType.OnEND)
	public void end(){
		if(sc != null)
			sc.close();
	}
}
