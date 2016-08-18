package cooker.editor;

import cooker.core.scripter.Cook;
import cooker.core.scripter.JSONReader;
import cooker.core.scripter.RecipeTCA;

public class ConsoleEditor {
	public static void main(String[] args) {
		
		/*
		String t = "cooker.sample.ConsoleTrigger";
		String c = "cooker.sample.TextCondition";
		String ay = "cooker.sample.ConsoleAction";
		String an = "cooker.sample.DoNothingAction";
		
		RecipeTCA.Builder b = new RecipeTCA.Builder(t,c,ay,an);

				
		b.addLink(new Link(t, "command", c, "target"));
		b.addLink(new Link(t, "content", ay, "text"));
		*/
		
       
		JSONReader jr = new JSONReader();
		RecipeTCA r = jr.readFromFile("recipes/sample.json");
		Cook s = new Cook();
		s.cooking(r);
	}
}
