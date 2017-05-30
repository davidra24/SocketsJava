package comunication;

import files.SendFile;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramirez
 */
public class WriteSocket{

    private DataOutputStream dataOutputStream;
    private SendFile sendFile;
    
    public WriteSocket(Socket socket){
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.sendFile = new SendFile(socket);
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeWrite(){
        try {
            dataOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(int value){
        try {
            dataOutputStream.writeInt(value);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(boolean b){
        try {
            dataOutputStream.writeBoolean(b);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(String s) {
        try {
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean writeFile(String filename){
        return this.sendFile.SendFile(filename);
    }
    
}
