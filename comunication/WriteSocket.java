package comunication;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramirez
 */
public class WriteSocket{

    private BufferedWriter bufferedWriter;
    private DataOutputStream dataOutputStream;
    
    public WriteSocket(Socket socket){
        try {
            //bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeWrite(){
        try {
//            bufferedWriter.close();
            dataOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void write(String s) {
        try {
            dataOutputStream.writeUTF(s);
//            bufferedWriter.write(s, 0, s.length());
        } catch (IOException ex) {
            Logger.getLogger(WriteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
