package cooker.core.scripter.service;

import cooker.core.debug.CookerException;
import cooker.core.debug.CookerLogger;

public class CookerServicer extends Thread implements ICookerServicer{

	public static final String TAG = CookerServicer.class.getSimpleName();
	private boolean isRunning = false;
	
	private ICookerServiceListener listner;
	
	public CookerServicer(ICookerServiceListener listener){
		this.listner = listener;
	}
	
	public void setOnCookerServiceListener (ICookerServiceListener listener){
		this.listner = listener;
	}
	
	@Override
	public void start(){
		isRunning = true;
		listner.onStart(this);
		CookerLogger.logln(TAG, "Start service id %d - %s", getId(), getName());
		super.start();
	}
	
	@Override
	public synchronized void run(){
		try{
			while(isRunning){
				if(listner.isWait(this)){
					CookerLogger.logln(TAG, "Wait to be fired by trigger...");
					listner.onWait(this);
					wait();
				}
				if(isRunning){
					CookerLogger.logln(TAG, "Rerun");
					listner.onRerun(this);
				}else{
					break;
				}
			}
		}catch(InterruptedException e){
			new CookerException(e.getMessage()).printStackTrace();
		}
	}
	
	@Override
	public synchronized void fire(){
		CookerLogger.logln(TAG, "Fire!");
		notifyAll();
	}	
	
	@Override
	public void end(){
		isRunning = false;
		fire();
		listner.onEnd(this);
		super.interrupt();
		CookerLogger.logln(TAG, "End service id %d - %s", getId(), getName());
	}
}
