package Packet;

import java.io.*;

public class PacketManager {
	DataInputStream reader;
	DataOutputStream writer;
	boolean connected;
	public PacketManager(DataInputStream reader, DataOutputStream writer) {
		this.reader = reader;
		this.writer = writer;
		connected = true;
	}
	public void write(int data) {
		try {
			writer.write(data);
			writer.flush();
		} catch (IOException e) { disconnect(); }
	}
	public void writeShort(int data) {
		try {
			writer.writeShort(data);
			writer.flush();
		} catch (IOException e) { disconnect(); }
	}
	public void writeInt(int data) {
		try {
			writer.writeInt(data);
			writer.flush();
		} catch (IOException e) { disconnect(); }
	}
	public void writeLong(long data) {
		try {
			writer.writeLong(data);
			writer.flush();
		} catch (IOException e) { disconnect(); }
	}
	public void writeString(String str) {
		try {
			byte[] b = str.getBytes("utf-8");
			writer.writeShort(b.length);
			writer.flush();
			writer.write(b);
			writer.flush();
		} catch (IOException e) {
			disconnect();
		}
	}
	public void writeFile(String fPath) {
		File file = new File(fPath);
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = bis.read(data)) != -1) {
                writer.write(data, 0, len);
            }
            writer.flush();
            bis.close();
            fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeHeader(int header) {
		writeShort(header);
	}
	public int read() {
		int ret = -1;
		try {
			ret = reader.readByte();
		} catch (IOException e) { disconnect(); }
		return ret;
	}
	public int readShort() {
		int ret = -1;
		try {
			ret = reader.readShort();
		} catch (IOException e) { disconnect(); }
		return ret;
	}
	public int readInt() {
		int ret = -1;
		try {
			ret = reader.readInt();
		} catch (IOException e) { disconnect(); }
		return ret;
	}
	public long readLong() {
		long ret = -1;
		try {
			ret = reader.readLong();
		} catch (IOException e) { disconnect(); }
		return ret;
	}
	public String readString() {
		int len = readShort();
		byte[] b = new byte[len];
		String ret = null;
		try {
			reader.read(b);
			ret = new String(b, "utf-8");
		} catch (IOException e) { disconnect(); }
		return ret;
	}
	public void getFile(String fName) {
		File file = new File(fName);
		FileOutputStream fos;
		BufferedOutputStream bos;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(writer);
			int len;
			int size = 4096;
			byte[] data = new byte[size];
			while((len = reader.read(data)) != -1) {
				bos.write(data, 0, len);
				bos.flush();
				bos.close();
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int readHeader() {
		return readShort();
	}
	public boolean isConnected() {
		return connected;
	}
	public void disconnect() {
		try {
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		connected = false;
	}
}
