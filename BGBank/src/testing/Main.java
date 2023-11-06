package testing;

import java.util.ArrayList;

import components.Client;

// 1.1.2 Creation of main class for tests
public class Main {

	public static void main(String[] args) {
		ArrayList<Client> arrayClient = generateClients(10);

		displayClients(arrayClient);
	}

	private static ArrayList<Client> generateClients(int toCreate) {
		ArrayList<Client> arrayClient = new ArrayList<Client>();
		for (int i = 0; i < toCreate; i++) {
			arrayClient.add(new Client("name" + i, "firstname" + i));
		}

		return arrayClient;
	}

	private static void displayClients(ArrayList<Client> arrayClient) {
		arrayClient.forEach(client -> System.out.println(client));
	}

}
