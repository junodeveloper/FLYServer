package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

public class FTPManager {
	
	private FTPClient client = null;
	
	public void init(String host, int port, String userName, String password) {
        client = new FTPClient();
        client.setControlEncoding("euc-kr");

        FTPClientConfig config = new FTPClientConfig();
        client.configure(config);
        try {
            client.connect(host, port);
            client.login(userName, password);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void upload(String dir, File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            client.storeFile(dir + file.getName(), in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void download(String dir, String downloadFileName, String path) {
        FileOutputStream out = null;
        InputStream in = null;
        dir += downloadFileName;
        try {
            in = client.retrieveFileStream(dir);
            out = new FileOutputStream(new File(path));
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void disconnect() {
        try {
            client.logout();
            if (client.isConnected()) {
                client.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
