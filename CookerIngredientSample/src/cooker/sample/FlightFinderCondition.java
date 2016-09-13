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
		if(from.equals("����") && to.equals("�λ�")){
			price = String.valueOf(50000);
			message = String.format("%s - %s ������ %s�Դϴ�.", from, to, price);
			return true;
		}else if(from.equals("����") && to.equals("����")){
			price = String.valueOf(40000);			
			message = String.format("%s - %s ������ %s�Դϴ�.", from, to, price);
			return true;
		}else{
			message = String.format("%s - %s �� �ش��ϴ� �װ����� �����ϴ�.", from, to);
			return false;
		}
			
			
	}
	
}
