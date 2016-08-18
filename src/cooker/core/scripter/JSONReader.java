package cooker.core.scripter;

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

public class JSONReader {
	
	public RecipeTCA readFromFile(String path){
		try {
			JSONParser jsonParser = new JSONParser();
	        String jsonInfo = readFile(path, Charset.defaultCharset());
	        JSONObject root = (JSONObject) jsonParser.parse(jsonInfo);
	        Class<?> trigger = getClazz((JSONArray) root.get("trigger"));
	        Class<?> condition = getClazz((JSONArray) root.get("condition"));
	        Class<?> actionY = getClazz((JSONArray) root.get("actionY"));
	        Class<?> actionN = getClazz((JSONArray) root.get("actionN"));
        
	        RecipeTCA.Builder b = new RecipeTCA.Builder(trigger, condition, actionY, actionN);
	        
	        JSONArray links = (JSONArray) root.get("links");
	        for (int i = 0; i < links.size(); i++) {
	        	JSONArray arr = (JSONArray) links.get(i);
	        	String from = arr.get(0).toString();
	        	String fromKey = arr.get(1).toString();
	        	String to = arr.get(2).toString();
	        	String toKey = arr.get(3).toString();
	        	Link link = new Link(from, fromKey, to, toKey);
	        	b.addLink(link);
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
	
	private Class<?> getClazz(JSONArray array){
		if(array.size() > 2)
			return null;
		String jarName = (String) array.get(0);
        String className = (String) array.get(1);
        return getClazz(jarName, className);
	}
	
	private Class<?> getClazz(String jarName, String className){
		try {
			String jarFilePath = "classes/" + jarName + ".jar";
			File jarFile = new File(jarFilePath);
			URL classURL = new URL("jar:" + jarFile.toURI().toURL() + "!/");
			System.out.println(classURL+className);
			@SuppressWarnings("resource")			
			URLClassLoader classLoader = new URLClassLoader(new URL [] {classURL});
			return classLoader.loadClass(className);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
