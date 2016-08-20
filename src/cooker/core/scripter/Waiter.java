package cooker.core.scripter;

public class Waiter implements TriggerWaiter{

	private Thread thread;
	private Cooking cooking;
	
	public void serve(Cooking cooking){
		this.cooking = cooking;
		thread = new Thread(cooking);
		thread.start();		
	}
	
	public void keepServing(){
		cooking.keepServing();
	}
	
	public void clear(){
		if(thread == null)
			return;
		thread.interrupt();		
		thread = null;		
	}
}
