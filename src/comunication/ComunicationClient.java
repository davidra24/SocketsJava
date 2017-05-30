package comunication;

import constants.ConstantsClient;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
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
    private boolean status;
    private boolean repo;
    

    public ComunicationClient(Socket socket) {        
        this.socket = socket;
        this.sc = new Scanner(System.in);
        this.listenSocket = new ListenSocket(socket);
        this.writeSocket = new WriteSocket(socket);
        this.status = false;
    }
    
    private synchronized void startMessage() throws InterruptedException {
        boolean loop;
        System.out.print(this.listenSocket.readString());
        int sel = sc.nextInt();
        this.writeSocket.write(sel);
        do{
            repo = (sel==0);
            switch (sel) {
                case 1:
                    loop = false;
                    System.out.print(this.listenSocket.readString());
                    int val = sc.nextInt();
                    this.writeSocket.write(val);
                    this.actions(val);
                    break;
                case 2:                    
                    loop = false;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(socket.isConnected()){
                                if (status) {
                                    listenSocket.readFile(ConstantsClient.PATHFILES);
                                }else{
                                    status = listenSocket.readBoolean();
                                }
                            }
                        }
                    });
                    t.run();
                    break;
                default:
                    System.out.print(this.listenSocket.readString());
                    sel = sc.nextInt();
                    loop = true;
                    break;
            }
        }while (loop);
    }
    
    private synchronized void actions(int value){
        boolean loop = false;
                    
        switch (value){
            
            case 1:
                System.out.print(listenSocket.readString());
                String filePath = "";
                while (filePath.isEmpty()) {
                    filePath = sc.nextLine();
                }
                writeSocket.writeFile(filePath);
                break;
            case 2:
                System.out.println("Lista de archivos");
                this.listOfFiles().forEach((listOfFile) -> {
                    System.out.println(listOfFile);
                });
                break;
            default:                
                break;
        }
    }

    private synchronized void comunication() throws InterruptedException, IOException{
        if (socket.isConnected()) {
            startMessage();
        }else{            
            writeSocket.closeWrite();
            listenSocket.CloseBuffer();
            socket.close();
        }
    }
    
    private void isSending(){
        writeSocket.write(status);
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                comunication();
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ComunicationClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ComunicationClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isRepo() {
        return repo;
    }
    
    public ArrayList<String> listOfFiles(){
        ArrayList al = new ArrayList();
        File f = new File(ConstantsClient.PATHFILES); 
        if (!f.exists()) {
            f.mkdirs();
        }
        File[] array = new File(ConstantsClient.PATHFILES).listFiles();
        for (File array1 : array) {
            al.add(array1.getName());
        }            
        return al;
    }
}
