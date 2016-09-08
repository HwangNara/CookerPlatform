package cooker.editor.gui;

import java.awt.Dimension;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class SouthPanel extends JScrollPane{
	private static final long serialVersionUID = -7418312838503887652L;
	
	public SouthPanel(){
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	}
	
	public void init(){
		JTextArea textArea = new JTextArea();
		TextAreaOutputStream guiConsole = new TextAreaOutputStream(textArea);
		setViewportView(textArea);
        setPreferredSize(new Dimension(600, 200));
		System.setOut(new PrintStream(guiConsole));
		Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(border);
	}

}
