package client;

import comunication.ComunicationClient;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author David Ramirez
 */
public class Client extends Thread {

    private String ip;
    private int port;
    private Socket socket;
    private ComunicationClient comunicationClient;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.socket = new Socket(ip, port);
        comunicationClient = new ComunicationClient(this.socket);
    }

    public synchronized void comunicationServer() {
        if(!comunicationClient.isAlive()){
            comunicationClient.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            comunicationServer();
        }
    }

    public boolean connectionStatus() {
        return this.socket.isConnected();
    }
}
