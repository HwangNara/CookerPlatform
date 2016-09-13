package cooker.editor.gui;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cooker.core.scripter.recipes.Cooking;
import cooker.core.scripter.recipes.chef.Chef;
import cooker.core.scripter.service.CookerServicer;
import cooker.editor.io.FileReader;

public class CenterPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -2572674342029436745L;
	
	private Choice choice = new Choice();
	private JButton btnCook = new JButton("Cook");
	private CookerGUI main;
	
	public CenterPanel(CookerGUI main){
		this.main = main;
	}
	
	public void init(){
		add(choice);
		add(btnCook);
		Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(border);
		btnCook.addActionListener(this);
		for (String name : FileReader.readFileNames(main.recipePathRoot)){
			addChoice(name);
		}
	}
	
	public void addChoice(String str){
		choice.add(str);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String filePath = main.recipePathRoot + choice.getSelectedItem();
		Chef chef = new Chef();
		File file = new File(filePath);
		chef.readRecipe(file, Charset.defaultCharset());
		Cooking cooking = chef.cook();
		CookerServicer servicer = new CookerServicer(cooking);
		servicer.start();
	}
}
