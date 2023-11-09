package components;

import java.beans.ConstructorProperties;

public class Debit extends Flow {

	@ConstructorProperties({ "comment", "amount", "targetAccountNumber" })
	public Debit(String comment, double amount, int targetAccountNumber) {
		super(comment, amount, targetAccountNumber);
	}

}
