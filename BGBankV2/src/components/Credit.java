package components;

import java.beans.ConstructorProperties;

public class Credit extends Flow {

	@ConstructorProperties({ "comment", "amount", "targetAccountNumber" })
	public Credit(String comment, double amout, int targetAccountNumber) {
		super(comment, amout, targetAccountNumber);
	}

}
