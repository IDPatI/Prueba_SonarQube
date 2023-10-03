package Server;

/*

 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import Server.ModeloServer.actualizableImpServer;
import Server.ModeloServer.actualizableImpServer;


public class EchoClientHandler extends Thread{
    private Socket clienteSocket;
    private PrintWriter out;
    private BufferedReader in;
    private actualizableImpServer actualizable;

    public EchoClientHandler(Socket aceptar) {
        this.clienteSocket = aceptar;
    }
    
    @Override
    public void run(){
        try {
            out = new PrintWriter(clienteSocket.getOutputStream(), true);
            //out.println("Conección hecha");
            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            System.out.println("Conexion hecha");
            String in2;
            while((in2 =in.readLine()) != null){
                String mensaje = in2;
                System.out.println("in2: "+ mensaje);
                String devolver = elegirAccion(mensaje);
                out.println(devolver);
                System.out.println("respuesta"+devolver);
                /*
                JSONObject jsonObject = new JSONObject(in2);
                this.actualizable = new actualizableImpServer();
                switch(jsonObject.getString("servicio")){
                    case "contar":
                        JSONObject contObj=actualizable.contarObjBitacora();
                        out.println(contObj.toString());
                        continue;
                    case "votar":
                        JSONObject votObj=actualizable.votarJSON(jsonObject);
                        System.out.println(votObj.toString());
                        out.println(votObj.toString());
                        continue;
                    case "registrar":
                        JSONObject regObj=actualizable.registroJSONBitacora(jsonObject);
                        out.println(regObj.toString());
                        continue;
                    case "listar":
                        JSONObject listObj=actualizable.listarJSONVotos();
                        out.println(listObj.toString());
                        continue;
                    default:
                        out.println("Adios cliente" +clienteSocket.getPort());
                        System.out.println("Cliente desconectado");
                        break;
                    */
            }
            out.println("Cerrando...");
            out.close();
            clienteSocket.close();
        }catch(IOException ex) { 
            System.out.println("Echo server: "+ ex.getMessage());
        }    
    }

    public String elegirAccion(String accion){
            JSONObject jsonObject = new JSONObject(accion);
            this.actualizable = new actualizableImpServer();
            switch(jsonObject.getString("servicio")){
                case "contar":
                    JSONObject contObj=actualizable.contarObjBitacora();
                    return contObj.toString();
                case "votar":
                    JSONObject votObj=actualizable.votarJSON(jsonObject);
                    return votObj.toString();
                case "registrar":
                    JSONObject regObj=actualizable.registroJSONBitacora(jsonObject);
                    return regObj.toString();
                case "listar":
                    JSONObject listObj=actualizable.listarJSONVotos();
                    return listObj.toString();
                default:
                    return "Adios cliente" +clienteSocket.getPort();

            }
    }
    
}
