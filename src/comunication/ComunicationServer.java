package comunication;

import constants.ConstantsServer;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author David Ramirez
 */
public class ComunicationServer extends Thread {

    private final Socket socket;
    private final WriteSocket writeSocket;
    private final ListenSocket listenSocket;
    private boolean send;
    
    public ComunicationServer(Socket socket){
        this.socket = socket;
        this.writeSocket = new WriteSocket(socket);
        this.listenSocket = new ListenSocket(socket);
        this.send = false;
    } 
    
    private void startMessage() throws IOException, ClassNotFoundException, InterruptedException {
        boolean loop;
        this.writeSocket.write(ConstantsServer.MESSAGECONNECTION);
        int h = this.listenSocket.readInt();
        do{
            switch (h) {
                case 1:
                    this.writeSocket.write(ConstantsServer.MESSAGEACTION);
                    this.selecction(listenSocket.readInt());
                    loop = false;
                    break;
                case 2:          
                    loop = false;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(socket.isConnected()){
                                if (send) {
                                    writeSocket.writeFile(ConstantsServer.PATHFILES);
                                } else {
                                    writeSocket.write(false);
                                }                                
                            }
                        }
                    });
                    t.run();
                     break;
                default:
                    this.writeSocket.write(ConstantsServer.MESSAGECONNECTION);
                    h = this.listenSocket.readInt();
                    loop = true;
                    break;
            }
        } while (loop);
    }
    
    private synchronized void selecction(int value){
        switch (value) {
            case 1:
                writeSocket.write("Digite la ruta del archivo a enviar: ");
                listenSocket.readFile(ConstantsServer.PATHFILES);
                break;
            case 2:
                writeSocket.write(false);
                break;
            default:
                break;
        }
    }

    private synchronized void comunication() throws IOException, ClassNotFoundException, InterruptedException{
        if(socket.isConnected()){
            startMessage();
        }else{
            
        }
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
    
    public Socket getSocket() {
        return this.socket;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
    
    

}