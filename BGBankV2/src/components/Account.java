package components;

// 1.2.1 Creation of the account class
@XmlRootElement(name = "Account")
@XmlType(propOrder = { "label", "client" })
public abstract class Account implements Comparable<Account> {

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

	public String getLabel() {
		return label;
	}

	@XmlAttribute(name = "label")
	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(Flow flow) {
		double amount = flow.getAmount();
		if (flow instanceof Credit) {
			this.balance = this.balance + amount;
		} else if (flow instanceof Transfert) {
			Transfert transf = (Transfert) flow;
			if (flow.getTargetAccountNumber() == this.getAccountNumber()) {
				this.balance = this.balance + amount;
			}
			if ((transf).getIssuingAccountNumber() == this.getAccountNumber()) {
				this.balance = this.balance - amount;
			}
		} else {
			this.balance = this.balance - amount;
		}
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public Client getClient() {
		return client;
	}

	@XmlAttribute(name = "client")
	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Accounts [label=" + label + ", balance=" + balance + ", client=" + client + "]";
	}

	// override equals and hashCode
	@Override
	public int compareTo(Account account) {
		return (int) (this.balance - account.getBalance());
	}
}
