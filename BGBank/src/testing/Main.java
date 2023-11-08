package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfert;

// 1.1.2 Creation of main class for tests
// 1.2.3 Creation of the table account
public class Main {

	public static void main(String[] args) {
		ArrayList<Client> arrayClient = generateClients(10);
		ArrayList<Account> arrayAccount = generateAccounts(arrayClient);

		HashMap<Integer, Account> hashAccount = generateHashAccount(arrayAccount);

		ArrayList<Flow> arrayFlow = generateFlow(arrayAccount);

		// Updating account
		HashMap<Integer, Account> updatedHashAccount = updateAccounts(hashAccount, arrayFlow);

		displayClients(arrayClient);
		displayAccounts(arrayAccount);
		displaySortedHash(updatedHashAccount);
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

	private static void displaySortedHash(HashMap<Integer, Account> hashAccount) {
		List<Entry<Integer, Account>> sorted = hashAccount.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.toList();

		for (Entry<Integer, Account> entry : sorted) {
			System.out.println(entry.getValue());
		}

	}

	// 1.3.4 Creation of the flow array
	private static ArrayList<Flow> generateFlow(ArrayList<Account> arrayAccount) {
		ArrayList<Flow> arrayFlow = new ArrayList<>();

		arrayFlow.add(new Debit("a debit of 50€ from account n°1", 50, 0));
		for (int i = 0; i < arrayAccount.size(); i++) {
			if (arrayAccount.get(i) instanceof CurrentAccount) {
				arrayFlow.add(new Credit("100.50€ on all current accounts", 100.50, i));
			} else {
				arrayFlow.add(new Credit("1500€ on all saving accounts", 1500, i));
			}
		}
		arrayFlow.add(new Transfert("A transfer of 50 € from account n ° 1 to account n ° 2", 50, 0, 1));
		return arrayFlow;
	}

	private static HashMap<Integer, Account> updateAccounts(HashMap<Integer, Account> hashAccount,
			ArrayList<Flow> arrayFlow) {

		for (Flow flow : arrayFlow) {
			Account account = hashAccount.get(flow.getTargetAccountNumber());
			account.setBalance(flow);
			hashAccount.put(account.getAccountNumber(), account);
			if (flow instanceof Transfert) {
				Transfert transf = (Transfert) flow;
				Account accountIssuing = hashAccount.get(transf.getIssuingAccountNumber());
				accountIssuing.setBalance(flow);
				hashAccount.put(accountIssuing.getAccountNumber(), accountIssuing);
			}
		}
		return hashAccount;
	}
}
