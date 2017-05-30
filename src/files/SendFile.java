package files;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramírez
 */
public class SendFile {
    private final Socket socket;

    public SendFile(Socket socket) {
        this.socket = socket;
    }
    
    public boolean SendFile(String filename){
        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            String str = "start";


                if (!str.equals("stop")) {

                    System.out.println("Envíando archivo: " + filename);
                    dout.writeUTF(filename);
                    dout.flush();

                    File file = new File(filename);
                    System.out.println(filename);
                    
                    FileInputStream fin = new FileInputStream(file);
                    long sz = (int) file.length();

                    byte b[] = new byte[1024];

                    int read;

                    dout.writeLong(sz);//writeUTF(Long.toString(sz));
                    dout.flush();

                    System.out.println("Peso del archivo: " + sz);

                    while ((read = fin.read(b)) != -1) {
                        dout.write(b, 0, read);
                        dout.flush();
                    }

                    System.out.println("..ok");
                    dout.flush();
                }
                dout.writeUTF("stop");
                System.out.println("Envio completo");
                dout.flush();
                //din.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SendFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }    
}
