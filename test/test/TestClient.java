package test;

import client.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ramírez
 */
public class TestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 9000);
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
