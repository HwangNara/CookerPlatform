package cooker.editor.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

public class CookerGUI {
	
	public Properties properties;
	public String recipePathRoot;

	private CenterPanel center;
	private SouthPanel south;
	
	public CookerGUI() throws IOException{
		InputStream is = new FileInputStream("console.properties");
		properties = new Properties();
		properties.load(is);
		recipePathRoot = properties.get("recipe_path").toString();
		openGUI();
	}
	
	private void openGUI(){
		JFrame jFrame = new JFrame("Cookr GUI");
		BorderLayout border = new BorderLayout(1,1);
		jFrame.setLayout(border);
		Container container = jFrame.getContentPane();
		this.center = new CenterPanel(this);
		this.south = new SouthPanel();
		this.center.init();
		this.south.init();
		container.add(south, BorderLayout.SOUTH);		
		container.add(center, BorderLayout.CENTER);
		jFrame.setSize(600, 400);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws IOException  {
		new CookerGUI();
	}
}
