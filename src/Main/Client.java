package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import Packet.PacketManager;
import Packet.ServerPacketCreator;
import Packet.ServerPacketHandler;

public class Client implements Runnable {
	private Socket socket;
	private PacketManager wrMan;
	private ServerPacketCreator creator;
	private ServerPacketHandler handler;
	private Server server;
	
	private User user;
	private int clientId;
	
	public Client(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			wrMan = new PacketManager(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()));
		} catch (Exception e) { e.printStackTrace(); }
		handler = new ServerPacketHandler(this);
		creator = new ServerPacketCreator(this);
	}
	public void run() {
		creator.sendTestPacket();
		// Connection Start
		while(wrMan.isConnected()) {
			int header = wrMan.readHeader();
			handler.handle(header);
		}
		System.out.println("Client Thread <" + Thread.currentThread().getName() + "> Terminate.");
		server.removeClientFromServer(this);
	}
	public void finish() {
		try {
			socket.close();
			wrMan.disconnect();
		} catch (Exception e) { e.printStackTrace(); }
	}
	public PacketManager getPacketManager() {
		return wrMan;
	}
	public Server getServer() {
		return server;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getClientId() {
		return clientId;
	}
	public User getUser() {
		return user;
	}
	public String getIP() {
		return socket.getInetAddress().toString();
	}
	public void userSetting() {
		user = new User();
		user.setUserNumber(clientId);
		user.setName("aaaa" + Integer.toString(clientId));
	}
	public ServerPacketCreator getCreator() {
		return creator;
	}
}