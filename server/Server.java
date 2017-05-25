package server;

import comunication.ComunicationServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author David Ramirez
 */
public class Server extends Thread {

    private final ServerSocket ss;
    private final ArrayList<ComunicationServer> listConnections;
    private boolean clientConnected = false;

    public Server(int port) throws IOException {
        this.listConnections = new ArrayList();
        this.ss = new ServerSocket(port);
    }
    
    public void acceptClients() throws IOException {
//        while (true) {
            if (listConnections.add(new ComunicationServer(ss.accept()))){
                clientConnected = true;
                System.out.println(clientConnected ? "Cliente con IP: " + listConnections.get(listConnections.size() - 1).getSocket().getInetAddress() + " conectado" : "No conectado");                
            } else if (listConnections.isEmpty()) {
                clientConnected = false;
            }
//        }
    }

    public synchronized void comunicationClients() {
//        while (clientConnected) {
            for (int i = 0; i < listConnections.size(); i++) {
                if(!listConnections.get(i).isAlive()){
                    listConnections.get(i).start();
                }
            }
//        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                acceptClients();
                comunicationClients();
            } catch (IOException ex) {
            }
        }
    }
}