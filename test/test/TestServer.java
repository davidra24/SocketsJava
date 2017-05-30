package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author David Ramírez
 * 
 */
public class TestServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(9000);
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
