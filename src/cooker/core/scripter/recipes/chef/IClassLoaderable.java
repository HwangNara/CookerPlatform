package cooker.core.scripter.recipes.chef;

import java.util.List;

public interface ICustomClassLoader {

	ClassLoader getClassLoader(List<CookerURL> cookerURLs);
}
