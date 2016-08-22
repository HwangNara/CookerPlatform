package cooker.editor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cooker.core.scripter.recipes.TCARecipe;

public class JSONReader {
	
	public TCARecipe readFromFile(String path){
		try {
			JSONParser jsonParser = new JSONParser();
	        String jsonInfo = readFile(path, Charset.defaultCharset());
	        JSONObject root = (JSONObject) jsonParser.parse(jsonInfo);
	        Class<?> trigger = getClazz((JSONObject)root.get("trigger"));
	        Class<?> condition = getClazz((JSONObject) root.get("condition"));
	        Class<?> actionY = getClazz((JSONObject) root.get("actionY"));
	        Class<?> actionN = getClazz((JSONObject) root.get("actionN"));
        
	        TCARecipe.Builder b = new TCARecipe.Builder(trigger, condition, actionY, actionN);
	        
	        JSONArray links = (JSONArray) root.get("links");
	        for (int i = 0; i < links.size(); i++) {
	        	JSONObject link = (JSONObject) links.get(i);	        	
	        	JSONObject from = (JSONObject) link.get("from");
	        	JSONObject to = (JSONObject) link.get("to");
	        	b.addLink(from, to);
			} 
	        return b.build();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
		
	private String readFile(String path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	private Class<?> getClazz(JSONObject tca){
		String url = (String) tca.get("URL");
		String fileName = (String) tca.get("file");
        String className = (String) tca.get("className");
        return getClazz(url, fileName, className);
	}
	
	/**
	 * 현재 jar만 지원
	 * @param url
	 * @param fileName
	 * @param className
	 * @return
	 */
	private Class<?> getClazz(String url, String fileName, String className){
		try {
			String filePath = url+fileName;
			if(filePath.startsWith("http")){
				URL classURL = new URL("jar:" + url + fileName + "!/");
				System.out.println(classURL+className);
				@SuppressWarnings("resource")			
				URLClassLoader classLoader = new URLClassLoader(new URL [] {classURL});
				return classLoader.loadClass(className);
			}else{
				File file = new File(filePath);
				URL classURL = new URL("jar:" + file.toURI().toURL() + "!/");
				System.out.println(classURL+className);
				@SuppressWarnings("resource")			
				URLClassLoader classLoader = new URLClassLoader(new URL [] {classURL});
				return classLoader.loadClass(className);
			}
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
