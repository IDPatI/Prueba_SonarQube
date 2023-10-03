package Cliente;

import java.io.IOException;

import Broker.ClienteS;

public class PruebasServer {

    public static void main(String[] args) {
        ClienteS server = new ClienteS();
        try {
            String respuesta = server.mensajear("127.0.0.1", 6666, Mensajes.listar().toString());
            System.out.println(respuesta);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
