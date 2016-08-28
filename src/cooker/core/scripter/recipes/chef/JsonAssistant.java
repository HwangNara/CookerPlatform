package cooker.core.scripter.recipes.chef;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cooker.core.debug.CookerException;
import cooker.core.scripter.recipes.chef.Chef.RecipeType;

public class JsonAssistant implements IAssistantable{

	protected Charset encoding = Charset.defaultCharset();
	protected JSONObject root;
	
	public JsonAssistant setEncoding(Charset encoding){
		this.encoding = encoding;
		return this;
	}
	
	public Charset getEncoding(){
		return encoding;
	}
		
	@Override
	public void init(File file) throws CookerException{
		Path path = file.toPath();
		JSONParser jsonParser = new JSONParser();			
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(path);
			String str = new String(encoded, encoding);
			root = (JSONObject) jsonParser.parse(str);
		} catch (IOException | ParseException e) {
			throw new CookerException("%s is not vaild path", path);
		}
	}
	
	@Override
	public RecipeType getRecipeType() throws CookerException{
		String type = (String) root.get("type");
		if(type == null)
			throw new CookerException("Recipe is required 'type' key.");	
		RecipeType recipeType = Enum.valueOf(Chef.RecipeType.class, type);
		if(recipeType == null)
			throw new CookerException("%s is not valid recipe type", type);
		return recipeType;
	}
	
	@Override
	public CookerURL getURL(String key) throws CookerException {
		JSONObject json = (JSONObject) root.get(key);
		String url = (String) json.get("URL");
		String fileName = (String) json.get("file");
		String className = (String) json.get("className");
		if(url == null)
			throw new CookerException("There is no 'URL' at %s", key);
		if(fileName == null)
			throw new CookerException("There is no 'file' at %s", key);
		if(className == null)
			throw new CookerException("There is no 'className' at %s", key);
		CookerURL cookerURL = new CookerURL(key, url, fileName, className);
		return cookerURL;
	}

	@Override
	public List<Pair<String, String>> getLinkPairs() throws CookerException {
		List<Pair<String, String>> pairs = new ArrayList<>();
		try {
			JSONArray array = (JSONArray) root.get("links");
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = (JSONObject) array.get(i);
				JSONObject from = (JSONObject) json.get("from");
				JSONObject to = (JSONObject) json.get("to");
				pairs.add(newLinkPair(from));
				pairs.add(newLinkPair(to));
			}
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			throw new CookerException(e.getMessage());
		}
		return pairs;
	}
	
	private Pair<String, String> newLinkPair(JSONObject json){
		String ingredient = (String) json.get("ingredient");
		String key = (String) json.get("key");
		return new Pair<String, String>(ingredient, key);
	}
}
