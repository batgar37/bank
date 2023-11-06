package components;

public abstract class Accounts {

	protected String label;
	protected double balance;
	protected static int accountNumber = 0;
	protected Client client;

	protected Accounts(String label, Client client) {
		this.label = label;
		this.client = client;
	}

	protected String getLabel() {
		return label;
	}

	protected void setLabel(String label) {
		this.label = label;
	}

	protected double getBalance() {
		return balance;
	}

	protected void setBalance(double balance) {
		this.balance = balance;
	}

	protected static int getAccountNumber() {
		return accountNumber;
	}

	protected static void setAccountNumber(int accountNumber) {
		Accounts.accountNumber = accountNumber;
	}

	protected Client getClient() {
		return client;
	}

	protected void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Accounts [label=" + label + ", balance=" + balance + ", client=" + client + "]";
	}

}
