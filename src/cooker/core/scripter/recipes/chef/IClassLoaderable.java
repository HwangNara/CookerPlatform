package cooker.core.scripter.recipes.chef;

import java.util.List;

public interface IClassLoaderable {

	ClassLoader getClassLoader(List<CookerURL> cookerURLs);
}
