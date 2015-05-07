package Main;
import java.net.*;

import Database.DBManager;
import Database.FTPManager;
import Database.PHPManager;

public class Server {
 
	public static final int DEBUG_VERSION = 2;
	public static final int PORT = 9808;
 
	ServerSocket server;
	ClientList clients;
	
	DBManager db;
	FTPManager ftp;
	PHPManager php;
	
	int lastConnectionNum;

	public Server() {
		try {
			server = new ServerSocket(PORT);
			//db = new DBManager();
			//ftp = new FTPManager();
			//ftp.init("",21,"","");
			php = new PHPManager();
		} catch (Exception e) { e.printStackTrace(); }
		lastConnectionNum = 0;
	}
	
	public void run() {
		print("Debug Version : " + DEBUG_VERSION);
		clients = new ClientList();
		waitClient();
		ftp.disconnect();
	}
	
	private void waitClient() {
		print("Start accepting new client.");
		while(true) {
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (Exception e) { e.printStackTrace(); }
			System.out.println("Connection Request From " + socket.getInetAddress().toString() + "...");
			addClientToServer(socket);
			print("Client<" + (lastConnectionNum - 1) + "> Connected. (Total : " + clients.getClientNum() + ")");
		}
	}
	
	public void addClientToServer(Socket socket) {
		Client client = new Client(this, socket);
		client.setClientId(lastConnectionNum);
		Thread thread = new Thread(client);
		thread.setName(Integer.toString(lastConnectionNum));
		thread.start();
		clients.addClient(client);
		lastConnectionNum++;
	}
	
	public void removeClientFromServer(Client client) {
		clients.removeClient(client);
	}
	
	public DBManager getDB() {
		return db;
	}
	
	public FTPManager getFTP() {
		return ftp;
	}
	
	public void print(String msg) {
		System.out.println(msg);
	}
	
}