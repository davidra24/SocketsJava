package comunication;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramírez
 */
public class ComunicationServer extends Thread {

    private static final String MESSAGECONNECTION = "Se ha conectado \n¿Qué desea hacer? \n1. Repositorio \n2. Cliente: ";
    private final Socket socket;
    private final WriteSocket writeSocket;
    private final ListenSocket listenSocket;
    
    public ComunicationServer(Socket socket) {
        this.socket = socket;
        writeSocket = new WriteSocket(socket);
        listenSocket = new ListenSocket(socket);
    }

    private void startMessage() throws IOException, ClassNotFoundException, InterruptedException {
        writeSocket.write(MESSAGECONNECTION);
        System.out.println(listenSocket.read());
    }

    private synchronized void comunication() throws IOException, ClassNotFoundException, InterruptedException{
        if(socket.isConnected()){
            startMessage();
        }else{
            writeSocket.closeWrite();
            listenSocket.CloseBuffer();
        }
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                comunication();
                sleep(1000);
            } catch (InterruptedException | IOException | ClassNotFoundException ex) {
                Logger.getLogger(ComunicationServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}