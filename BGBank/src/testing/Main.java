package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;

// 1.1.2 Creation of main class for tests
// 1.2.3 Creation of the table account
public class Main {

	public static void main(String[] args) {
		ArrayList<Client> arrayClient = generateClients(10);
		ArrayList<Account> arrayAccount = generateAccounts(arrayClient);

		HashMap<Integer, Account> hashAccount = generateHashAccount(arrayAccount);

		displayClients(arrayClient);
		displayAccounts(arrayAccount);
		displayHash(hashAccount);

	}

	private static ArrayList<Client> generateClients(int toCreate) {
		ArrayList<Client> arrayClient = new ArrayList<>();
		for (int i = 0; i < toCreate; i++) {
			arrayClient.add(new Client("name" + i, "firstname" + i));
		}

		return arrayClient;
	}

	private static void displayClients(ArrayList<Client> arrayClient) {
		arrayClient.forEach(System.out::println);
	}

	private static ArrayList<Account> generateAccounts(ArrayList<Client> arrayClient) {
		ArrayList<Account> arrayAccount = new ArrayList<>();
		for (Client client : arrayClient) {
			arrayAccount.add(new SavingsAccount("Saving account client" + client.getClientNumber(), client));
			arrayAccount.add(new CurrentAccount("Current account client" + client.getClientNumber(), client));
		}
		return arrayAccount;
	}

	private static void displayAccounts(ArrayList<Account> arrayAccount) {
		arrayAccount.forEach(System.out::println);
	}

	// 1.3.1 Adaptation of the table of accounts
	private static HashMap<Integer, Account> generateHashAccount(ArrayList<Account> arrayAccount) {
		HashMap<Integer, Account> hashtable = new HashMap<>();

		for (Account account : arrayAccount) {
			hashtable.put(account.getAccountNumber(), account);
		}

		return hashtable;
	}

	private static void displayHash(HashMap<Integer, Account> hashAccount) {
		hashAccount.entrySet().stream().sorted(Map.Entry.comparingByValue());
	}
}
