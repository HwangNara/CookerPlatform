package cooker.core.scripter.recipes.chef;

import java.util.List;

import cooker.core.debug.CookerException;
import cooker.core.scripter.recipes.chef.Chef.RecipeType;

public interface IRecipeReader {
	void init(String entireString) throws CookerException;
	RecipeType getRecipeType() throws CookerException;
	CookerURL getURL(String key) throws CookerException;
	List<Pair<String, String>> getLinkPairs() throws CookerException;
}
