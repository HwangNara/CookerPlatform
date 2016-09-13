package cooker.sample;

import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerSerializable;

public class FlightFinderCondition {
	
	@CookerSerializable(key="from")
	public String from;
	@CookerSerializable(key="to")
	public String to;
	
	@CookerSerializable(key="price")
	public String price;
	
	@CookerSerializable(key="message")
	public String message;
	
	@CookerCondition
	public boolean find(){
		if(from.equals("서울") && to.equals("부산")){
			price = String.valueOf(50000);
			message = String.format("%s - %s 가격은 %s입니다.", from, to, price);
			return true;
		}else if(from.equals("서울") && to.equals("광주")){
			price = String.valueOf(40000);			
			message = String.format("%s - %s 가격은 %s입니다.", from, to, price);
			return true;
		}else{
			message = String.format("%s - %s 에 해당하는 항공편이 없습니다.", from, to);
			return false;
		}
			
			
	}
	
}
