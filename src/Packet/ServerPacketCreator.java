package Packet;

import java.io.File;

import Database.ImageInfo;
import Main.Client;
import Main.Server;
import Main.User;

public class ServerPacketCreator {
	Client client;
	Server server;
	PacketManager wrMan;
	
	public ServerPacketCreator(Client client) {
		wrMan = client.getPacketManager();
		server = client.getServer();
		this.client = client;
	}
	public void testPacket() {
		wrMan.writeHeader(ClientRecvs.TEST_PACKET);
	}
	public void versionDiscord() {
		wrMan.writeHeader(ClientRecvs.VERSION_DISCORD);
	}
	public void showImage(ImageInfo image) {
		File file = new File(image.getPath());
		wrMan.writeHeader(ClientRecvs.RESPONSE_IMAGE);
		wrMan.writeFile(image.getPath());
	}
	public void sendTestPacket() {
		wrMan.writeHeader(ClientRecvs.TEST_PACKET);
	}
}
