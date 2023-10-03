package Modelo;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;

import org.json.JSONObject;

import Cliente.ClienteMain;
import Cliente.Mensajes;

public class PeticionesListar {
    


    public static String listarServiciosBroker(){
        String serviciosString;
        try {
            serviciosString  = ClienteMain.broker.sendMessage(Mensajes.listar());
            registrarEvento("Listar todos los servicios del broker, Broker: " + ClienteMain.portBroker);
        } catch (IOException e) {
            return "No se pudo enviar el mensaje al broker: \n" +e.getMessage() ;
        }
        JSONObject respuesta = new JSONObject(serviciosString);
        int respuestas = respuesta.getInt("respuestas");

        String devolver = "";
        for(int i=0; i< respuestas; i++){
            String respuestaA = respuesta.getString("respuesta"+(i+1));
            respuestaA  = respuestaA.toUpperCase();
            String valorA = respuesta.getString("valor"+(i+1));
            devolver += respuestaA +": "+ valorA + "\n";
        }
        return devolver;
    }

    public static String buscarServiciosBroker(String buscar){
        String serviciosString;
        try {
            serviciosString  = ClienteMain.broker.sendMessage(Mensajes.listar(buscar));
            registrarEvento("Buscar servicios del broker, Broker: " + ClienteMain.portBroker);
        } catch (IOException e) {
            return "No se pudo enviar el mensaje al broker: \n" +e.getMessage();
        }
        JSONObject respuesta = new JSONObject(serviciosString);
        int respuestas = respuesta.getInt("respuestas");
        String devolver = "";
        for(int i=0; i< respuestas; i++){
            String respuestaA = respuesta.getString("respuesta"+(i+1));
            String valorA = respuesta.getString("valor"+(i+1));
            devolver += respuestaA +": "+ valorA + "\n";
        }

        if(devolver.equals("")){
            devolver += "No se encontró encontró el servicio, trata de ponerlo todo en minusculas";
        }
        return devolver;
    }

    public static String ActualizarBitacora(){
        String serviciosString;
        try {
            serviciosString  = ClienteMain.broker.sendMessage(Mensajes.listarArchBit());
            registrarEvento("Listar Bitacora, Cliente: " + ClienteMain.portBroker);
        } catch (IOException e) {
            return "No se pudo enviar el mensaje al broker: \n" +e.getMessage();
        }

        JSONObject respuesta = new JSONObject(serviciosString);
        int respuestas = respuesta.getInt("respuestas");
        String devolver = "";
        for(int i=1; i< respuestas; i++){
            //String respuestaA = respuesta.getString("respuesta"+(i+1));
            String valorA = respuesta.getString("valor"+(i+1));
            devolver +=(i-1)+": "+ valorA + "\n";
        }
        return devolver;
    }

    public static void registrarEvento(String evento) throws IOException{
        LocalDateTime datetime = LocalDateTime.now();
        ClienteMain.broker.sendMessage(Mensajes.registrar(evento, datetime.toString()));
    }



    

}
