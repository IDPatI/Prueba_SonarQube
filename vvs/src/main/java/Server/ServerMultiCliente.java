package Server;

/*

 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMultiCliente {
    private ServerSocket serverSocket;
    

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerMultiCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
