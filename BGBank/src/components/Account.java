package components;

// 1.2.1 Creation of the account class
public abstract class Account {

	protected String label;
	protected double balance = 0;
	protected int accountNumber;
	protected static int count = 0;
	protected Client client;

	protected Account(String label, Client client) {
		this.label = label;
		this.client = client;
		this.accountNumber = count++;
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

	protected int getAccountNumber() {
		return this.accountNumber;
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
