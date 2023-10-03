package Server;

/*

 */

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

/**
 *
 * @author picar
 */
public class BrokerComm {
    private Socket clienteSocket;
    private PrintWriter out;
    public BufferedReader in;
    
    public void empezarConexion(String ip, int port) throws IOException{
        clienteSocket = new Socket(ip, port);
        out =new PrintWriter(clienteSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
        //new impesor(this);
    }
    
    public String realizarConexionBroker(JSONObject serverInfo){
        out.write(serverInfo.toString());
        String res ="Sin respuesta";
        try {
            res = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(BrokerComm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public void prueba(String mensaje){
        out.println(mensaje);
    }
    
    public void stop(){
        try {
            in.close();
            out.close();
            clienteSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(BrokerComm.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
