package cooker.core.scripter.recipes.chef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import cooker.core.debug.CookerLogger;
import dalvik.system.DexClassLoader;

public class DexClassLoaderable extends AsyncTask<CookerURL, Integer, ClassLoader> implements IClassLoaderable{

	private Activity activity;
	private ClassLoader output;
	
	public DexClassLoaderable(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public ClassLoader getClassLoader(List<CookerURL> cookerURLs) {
		CookerURL[] urls = new CookerURL[cookerURLs.size()];
		cookerURLs.toArray(urls);
		this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urls);
		while(output == null){
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return output;
	}	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();				
	}
	
	@Override
	protected ClassLoader doInBackground(CookerURL... cookerURLs) {
		StringBuilder sb = new StringBuilder();
		File dex = activity.getDir("dex", Context.MODE_PRIVATE);
		dex.mkdir();
		String urlStr = "";
		ClassLoader classLoader = null;
		try{
			for (int i = 0; i < cookerURLs.length; i++) {
				CookerURL cookerURL = cookerURLs[i];
				urlStr = cookerURL.url + cookerURL.fileName;
				
				URL url = new URL(urlStr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL을 연결한 객체 생성.
				conn.setRequestMethod("GET"); // get방식 통신
				conn.setDoOutput(true);       // 쓰기모드 지정
				conn.setDoInput(true);        // 읽기모드 지정
				conn.setUseCaches(false);     // 캐싱데이터를 받을지 안받을지
				conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정
				InputStream is = conn.getInputStream();
				
				File target = new File(dex, cookerURL.fileName);
				FileOutputStream fos = new FileOutputStream(target);
				byte[] buffer = new byte[0xFF];
				int len;  
				long size=0;
				while ((len = is.read(buffer)) > 0) {        	
					fos.write(buffer, 0, len);
					size += len;
				}
				fos.close();
				is.close();	        		
				publishProgress(i);
				CookerLogger.logln("DexClassLoaderable", "Download complete %s %d bytes", urlStr, size);
				CookerLogger.logln("DexClassLoaderable", "Writing complete %s %d bytes", target.getAbsolutePath(), size);
				sb.append(target.getAbsolutePath());
				sb.append(File.pathSeparator);
			}
			File fo = activity.getCodeCacheDir();
			classLoader = new DexClassLoader(sb.toString(), fo.getAbsolutePath(), null, activity.getClassLoader());
			synchronized (this) {
				output = classLoader;
				notifyAll();
			}
			return classLoader;
		}catch (IOException e) {
			CookerLogger.logln("DexClassLoaderable", "Download fail %s", urlStr);
			e.printStackTrace();
			return null;
		}  
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(ClassLoader result) {
		
	}
}
