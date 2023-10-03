package Broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
/**
 *
 * @author David Pat
 */
public class BrokerThreads extends Thread{
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public BrokerThreads(Socket socket) {
            this.clientSocket = socket;
    }

    @Override
        public void run(){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String mensaje = inputLine;
                String respuesta =Broker.s.parseMensaje(mensaje);
                out.println(respuesta);
                System.out.println(mensaje);
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Cliente desconectado: "+clientSocket.getPort());
            
        }
    }
    
}
