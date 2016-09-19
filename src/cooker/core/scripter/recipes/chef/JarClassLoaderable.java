package cooker.core.scripter.recipes.chef;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import cooker.core.debug.CookerException;

public class JarClassLoaderable implements IClassLoaderable{

	@Override
	public ClassLoader getClassLoader(List<CookerURL> cookerURLs) {
		URL[] urls = new URL[cookerURLs.size()];
		for (int i = 0; i < urls.length; i++) {
			try {
				urls[i] = cookerURLs.get(i).toURL();
			} catch (CookerException e) {
				urls[i] = null;
				e.printStackTrace();
			}
		}
		ClassLoader classLoader = new URLClassLoader(urls);
		return classLoader;
	}

}
