package Main;

import java.util.ArrayList;

public class ClientList {
	ArrayList<Client> clients;
	Server server;
	
	public ClientList() {
		clients = new ArrayList<Client>();
	}
	
	public int getClientNum() {
		return clients.size();
	}
	
	public void addClient(Client client) {
		clients.add(client);
	}
	
	public void removeClient(Client client) {
		clients.remove(client);
	}
	public Client getClient(String userName) {
		Client ret = null;
		for(Client client : clients) {
			if(client.getUser().getName() == userName) {
				ret = client;
				break;
			}
		}
		return ret;
	}
	public ArrayList<Client> getArrayList() {
		return clients;
	}
}
