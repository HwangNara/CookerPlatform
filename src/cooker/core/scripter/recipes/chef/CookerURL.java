package cooker.core.scripter.recipes.chef;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import cooker.core.debug.CookerException;

public class CookerURL{
	public static final String TAG = CookerURL.class.getSimpleName();
	public static final String[] SUPPORTS = new String[] {"jar", "dex"};
	
	public static boolean isSupportExtension(String extension){
		for (int i = 0; i < SUPPORTS.length; i++) {
			if(SUPPORTS[i].equalsIgnoreCase(extension))
				return true;
		}
		return false;
	}
	
	public final String key;	
	public final String url;
	public final String fileName;
	public final String extension;
	public final String className;	
	
	public CookerURL(String key, String url, String fileName, String extension, String className) {
		super();
		this.key = key;
		this.url = url;
		this.fileName = fileName;
		this.extension = extension;
		this.className = className;
	}

	public URL toURL() throws CookerException{
		try {
			URL url = new URL(getExtractionURI());
			return url;
		} catch (MalformedURLException e) {
			throw new CookerException(TAG, "Invalid URL %s", getExtractionURI());
		}
	}
	
	public String getURI(){
		String filePath = url + fileName;
		String URI = "unknown";
		if(url.startsWith("http")){
			URI = filePath;
		}else {
			URI = new File(filePath).toURI().toString();
		}
		return URI;
	}
	
	public String getExtractionURI(){
		return String.format("%s:%s!/", extension, getURI());
	}
	
	public String getFullURI(){
		return String.format("%s:%s!/%s", extension, getURI(), className);
	}
}
