package comunication;

import files.ReciveFile;
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
    private ReciveFile reciveFile;
    

    public ListenSocket(Socket socket){
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.reciveFile = new ReciveFile(socket);
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
    
    public int readInt(){
        try {
            return dataInputStream.readInt();
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public boolean readBoolean(){
        try {
            return dataInputStream.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public String readString(){
        String s = "";
        try {
            s = dataInputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ListenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    public boolean readFile(String directory) {
        return this.reciveFile.ReciveFile(directory);
    }
}
