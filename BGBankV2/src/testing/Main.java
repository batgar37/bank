package testing;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.FlowList;
import components.SavingsAccount;
import components.Transfert;

// 1.1.2 Creation of main class for tests
// 1.2.3 Creation of the table account
public class Main {

	public static void main(String[] args) {
		ArrayList<Client> arrayClient = generateClients(10);
		ArrayList<Account> arrayAccount = generateAccounts(arrayClient);

		HashMap<Integer, Account> hashAccount = generateHashAccount(arrayAccount);

		FlowList flows = generateFlow(arrayAccount);

		// Updating account
		HashMap<Integer, Account> updatedHashAccount = updateAccounts(hashAccount, flows);

		displayClients(arrayClient);
		displayAccounts(arrayAccount);
		displaySortedHash(updatedHashAccount);

		try {
			FlowList flowsJSON = generateFlowFromJSON("src/testing/flow.json");
			HashMap<Integer, Account> updatedHashAccountJSON = updateAccounts(hashAccount, flowsJSON);
			displaySortedHash(updatedHashAccountJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ArrayList<Account> accountsXML = generateAccountFromXML("src/testing/accounts.xml");
			displayAccounts(accountsXML);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	// Display accounts sorted by the value of the balance
	private static void displaySortedHash(HashMap<Integer, Account> hashAccount) {
		List<Entry<Integer, Account>> sorted = hashAccount.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.toList();

		for (Entry<Integer, Account> entry : sorted) {
			System.out.println(entry.getValue());
		}

	}

	// 1.3.4 Creation of the flow array
	private static FlowList generateFlow(ArrayList<Account> arrayAccount) {
		FlowList arrayFlow = new FlowList();

		arrayFlow.getFlows().add(new Debit("a debit of 50€ from account n°1", 50, 0));
		for (int i = 0; i < arrayAccount.size(); i++) {
			if (arrayAccount.get(i) instanceof CurrentAccount) {
				arrayFlow.getFlows().add(new Credit("100.50€ on all current accounts", 100.50, i));
			} else {
				arrayFlow.getFlows().add(new Credit("1500€ on all saving accounts", 1500, i));
			}
		}
		arrayFlow.getFlows().add(new Transfert("A transfer of 50 € from account n ° 1 to account n ° 2", 50, 0, 1));
		return arrayFlow;
	}

	// Update accounts using a list of flow
	private static HashMap<Integer, Account> updateAccounts(HashMap<Integer, Account> hashAccount, FlowList flows) {
		flows.getFlows().forEach(flow -> {
			Account account = hashAccount.get(flow.getTargetAccountNumber());
			account.setBalance(flow);
			hashAccount.put(account.getAccountNumber(), account);
			if (flow instanceof Transfert) {
				Transfert transf = (Transfert) flow;
				Account accountIssuing = hashAccount.get(transf.getIssuingAccountNumber());
				accountIssuing.setBalance(flow);
				hashAccount.put(accountIssuing.getAccountNumber(), accountIssuing);
			}
		});

		return hashAccount;
	}

	// 2.1 JSON file of flows
	private static FlowList generateFlowFromJSON(String path) throws IOException {

		// Get JSON file from path
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String json = new String(encoded, StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		return mapper.readValue(json, FlowList.class);
	}

	// 2.2 XML file of account
	private static ArrayList<Account> generateAccountFromXML(String path)
			throws ParserConfigurationException, SAXException, IOException {
		ArrayList<Account> accounts = new ArrayList<>();

		// Get the XML file from path
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new File(path));
		doc.getDocumentElement().normalize();

		// List the nodes named account
		NodeList nodeList = doc.getElementsByTagName("account");
		int n = nodeList.getLength();
		Node current;
		// Navigate in the nodeList and create an account for each
		for (int i = 0; i < n; i++) {
			current = nodeList.item(i);
			if (current.getNodeType() == Node.ELEMENT_NODE && "account".equals(current.getNodeName())) {
				Node type = current.getAttributes().getNamedItem("type");
				accounts.add(createAccountFromNode(current, type.getNodeValue()));
			}
		}
		return accounts;
	}

	// Create an account passing a node and a type
	private static Account createAccountFromNode(Node node, String type) {
		NodeList nodeList = node.getChildNodes();
		int n = nodeList.getLength();
		Node current;
		String label = null;
		Client client = new Client(null, null);
		for (int i = 0; i < n; i++) {
			current = nodeList.item(i);
			if (current.getNodeType() == Node.ELEMENT_NODE) {
				if ("label".equals(current.getNodeName())) {
					label = current.getTextContent();
				}
				if ("name".equals(current.getNodeName())) {
					client.setName(current.getTextContent());
				}
				if ("firstname".equals(current.getNodeName())) {
					client.setFirstName(current.getTextContent());
				}
			}
		}
		switch (type) {
		case "currentAccount":

			return new CurrentAccount(label, client);

		case "savingsAccount":

			return new SavingsAccount(label, client);

		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}
