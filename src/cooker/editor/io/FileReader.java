package cooker.editor.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

	public static List<String> readFileNames(String folderPath){
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		List<String> fileNames = new ArrayList<>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileNames.add(listOfFiles[i].getName());
			}
		}
		return fileNames;
	}
}
