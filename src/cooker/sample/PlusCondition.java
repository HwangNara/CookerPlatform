package cooker.sample;

import cooker.core.annotations.CookerCondition;
import cooker.core.annotations.CookerIngredient;
import cooker.core.annotations.CookerSerializable;

@CookerIngredient
public class PlusCondition {
	
	@CookerSerializable(key = "value1")
	public Integer value1;
	@CookerSerializable(key = "value2")
	public Integer value2;
	@CookerSerializable(key = "userAnswer")
	public Integer userAnswer;
	@CookerSerializable(key = "rightAnswer")
	public String rightAnswer;
	@CookerSerializable(key = "output")	
	public String output;
	
	@CookerCondition
	public boolean check(){
		int rightAnswer = value1 + value2;
		this.rightAnswer = String.valueOf(rightAnswer);
		boolean isCorrect = userAnswer == rightAnswer;
		if(isCorrect){
			output = "Correct!";
		}else{
			output = "Incorrect! Right answer is ";
		}
		System.out.printf("%d + %d = %d ?\n", value1, value2, userAnswer);
		return isCorrect;
	}
}
