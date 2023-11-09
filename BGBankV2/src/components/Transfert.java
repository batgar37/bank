package components;

import java.beans.ConstructorProperties;

public class Transfert extends Flow {

	private int issuingAccountNumber;

	@ConstructorProperties({ "comment", "amount", "targetAccountNumber", "issuingAccountNumber" })
	public Transfert(String comment, double amount, int targetAccountNumber, int issuingAccountNumber) {
		super(comment, amount, targetAccountNumber);
		this.issuingAccountNumber = issuingAccountNumber;
	}

	public int getIssuingAccountNumber() {
		return issuingAccountNumber;
	}

	public void setIssuingAccountNumber(int issuingAccountNumber) {
		this.issuingAccountNumber = issuingAccountNumber;
	}

}
