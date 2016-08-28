package cooker.core.scripter.service;

public interface ICookerServiceListener {

	void onStart(CookerServicer servicer);
	boolean isWait(CookerServicer servicer);
	void onWait(CookerServicer servicer);
	void onRerun(CookerServicer servicer);
	void onEnd(CookerServicer servicer);
}
