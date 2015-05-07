package Packet;

import java.util.ArrayList;

import Database.ImageInfo;
import Main.Client;
import Main.Server;

public class ServerPacketHandler {
	PacketManager wrMan;
	ServerPacketCreator creator;
	Client client;
	Server server;
	public ServerPacketHandler(Client client) {
		wrMan = client.getPacketManager();
		creator = new ServerPacketCreator(client);
		server = client.getServer();
		this.client = client;
	}
	public void handle(int header) {
		switch(header) {
		case ServerRecvs.TEST_PACKET:
			System.out.println("Test Succeed.");
			break;
		case ServerRecvs.REQUEST_IMAGE:
			requestImage();
			break;
		case ServerRecvs.UPLOAD_IMAGE:
			uploadImage();
			break;
		default:
			System.out.println("[Client " + client.getClientId() + "] Unknown Packet. (" + header + ")");
		}
	}
	public void initClient() {
		int clientVersion = wrMan.readShort();
		if(Server.DEBUG_VERSION == clientVersion) {
			client.userSetting();
		} else {
			System.out.println("Version discord at Client<" + client.getClientId() + ">.");
			creator.versionDiscord();
		}
	}
	public void uploadImage() {
		wrMan.getFile("temp.jpg");
	}
	public void requestImage() {
		double latitude = wrMan.readInt() / 1000000.0;
		double longitude = wrMan.readInt() / 1000000.0;
		ArrayList<ImageInfo> imageList = server.getDB().getImageList();
		for(ImageInfo image : imageList) {
			if(image.inRange(latitude, longitude)) {
				creator.showImage(image);
			}
		}
	}
}