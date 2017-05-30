package files;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramírez
 */
public class ReciveFile {

    private Socket socket;

    public ReciveFile(Socket socket) {
        this.socket = socket;
    }

    public boolean ReciveFile(String path) {
        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String filename = "";


            filename = din.readUTF();
            System.out.println("Recibira el archivo: " + filename);
            System.out.println("El archivo se guardará como: " + filename);
            filename = path + filename.substring(filename.indexOf("/") + 1, filename.length());

            long sz = din.readLong();//Long.parseLong(din.readUTF());
            System.out.println("Peso del archivo: " + (sz / (1024 * 1024)) + " MB");

            byte b[] = new byte[1024];
            System.out.println("Recibiendo archivo...");
            FileOutputStream fos = new FileOutputStream(new File(filename), true);
            long bytesRead;
            do {
                bytesRead = din.read(b, 0, b.length);
                fos.write(b, 0, b.length);
            } while (!(bytesRead < 1024));
            System.out.println("Archivo recibido");
            fos.close();
            //dout.close();
            return true;
        } catch (EOFException e){
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ReciveFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
