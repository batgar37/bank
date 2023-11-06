package components;

// 1.1.1 Creation of the client class
public class Client {

	private static int count = 0;
	private String name;
	private String firstName;
	private int clientNumber;

	public Client(String name, String firstName) {
		this.name = name;
		this.firstName = firstName;
		this.clientNumber = count++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", firstName=" + firstName + ", clientNumber=" + clientNumber + "]";
	}

}
