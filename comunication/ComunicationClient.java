package comunication;

import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramírez
 */
public class ComunicationClient extends Thread {

    private final Scanner sc;
    private final Socket socket;
    private final WriteSocket writeSocket;
    private final ListenSocket listenSocket;

    public ComunicationClient(Socket socket) {        
        this.socket = socket;
        sc = new Scanner(System.in);
        this.listenSocket = new ListenSocket(socket);
        this.writeSocket = new WriteSocket(socket);
    }
    
    private synchronized void startMessage() throws InterruptedException{
        System.out.println(listenSocket.read());
        writeSocket.write(sc.nextLine());
    }

    private synchronized void comunication() throws InterruptedException{
        if (socket.isConnected()) {
            startMessage();
        }else{
            writeSocket.closeWrite();
            listenSocket.CloseBuffer();
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                comunication();
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ComunicationClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
