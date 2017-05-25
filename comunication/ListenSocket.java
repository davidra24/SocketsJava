package comunication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramirez
 */
public class ListenSocket extends Thread {

    private DataInputStream dataInputStream;

    public ListenSocket(Socket socket){
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CloseBuffer(){
        try {
            dataInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String read(){
        String s = "";
        try {
            s = dataInputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}
