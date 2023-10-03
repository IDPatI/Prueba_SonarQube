/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.OctetStreamData;
import javax.xml.transform.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author David Pat
 */
public class Servicios {
    public JSONObject servidores;
    public JSONObject servicios;
    public Servicios(){
        servidores  = new JSONObject();
        servicios  = new JSONObject();
        servidores.accumulate("cantidad", 0);
    }
    public String parseMensaje(String mensaje){
        JSONObject objeto = new JSONObject(mensaje);
        try {
            String servicio= objeto.getString("servicio");
            //Method metodo = Servicios.class.getMethod(servicio,JSONObject.class, Servicios.class);
            switch (servicio) {
                case "registrar":
                    return registrar(objeto, this);
                case "listar":
                    return listar(objeto);
                case "ejecutar":
                    return ejecutar(objeto);
                
                default:
                    throw new AssertionError();
            }
            //return (String)metodo.invoke(this, objeto, this);
        } catch (SecurityException | IllegalArgumentException  ex) {
            System.out.println("Metodo no encontrado");
        }
        return "{}";
    }
    public String registrar(JSONObject objeto, Servicios s){
        int variables = objeto.getInt("variables");
        //Numero de registro del servidor
        int actual =s.servidores.getInt("cantidad");
        String servicio = "";
        
        for(int i=0; i<variables; i++){
            String var = "variable"+ Integer.toString(i+1);
            String val = "valor"+ Integer.toString(i+1);
            String variable = objeto.getString(var);
            Object valor  = objeto.get(val);
            
            
            switch(variable){
                case "servidor":
                    int busqueda =buscarServer((String)valor);
                    if(busqueda<=0){
                        s.servidores.increment("cantidad");
                        actual =s.servidores.getInt("cantidad");
                        s.servidores.accumulate(String.valueOf(actual), (String)valor);
                    }else{
                        actual = busqueda;
                    };
                    break;
                case "puerto":
                    s.servidores.accumulate(String.valueOf(actual), (int)valor);
                    break;
                case "servicio":
                    servicio =(String) valor;
                    s.servicios.append(servicio,String.valueOf(actual));
                    break;
                case "parametros":
                    s.servicios.append(servicio+"P", (int)valor);
            }
        }
        JSONObject respuesta  = new JSONObject();
        respuesta.accumulate("servicio","registrar");
        respuesta.accumulate("respuetas",1);
        respuesta.accumulate("respueta1","identificador");
        respuesta.accumulate("valor1",actual);
        return respuesta.toString();
    }
    private int buscarServer(String ip){
        int actual = servidores.getInt("cantidad");
        for(int i=0; i<actual; i++){
           String registrado= servidores.getJSONArray(String.valueOf(i+1)).getString(0);
            if(registrado.equals(ip)){
                return i+1;
            }
        }
        return 0;
    }
    
    public String listar(JSONObject objeto){
        int variables = objeto.getInt("variables");
        JSONObject respuesta  = new JSONObject();
        int respuestas =0;
        if(variables==0){
            /*
            while(llaves.hasNext()){
                String llaveA = (String)llaves.next();
                if(!llaveA.endsWith("P")){
                    
                    JSONArray actual= servicios.getJSONArray(llaveA);
                    for(int i=0; i<actual.length(); i++){
                        respuestas++;
                        respuesta.accumulate("respuesta"+respuestas, llaveA);
                        JSONArray ip= servidores.getJSONArray((String)actual.get(i));
                        respuesta.accumulate("valor"+respuestas,ip.get(0)+":"+ip.get(1));
                    }
                    
                }
            }*/
            return listarTodo();
        }else{
            String ser = objeto.getString("valor1");
            JSONArray actual;
            try{
                actual= servicios.getJSONArray(ser);
            }catch(JSONException js){
                actual = new JSONArray();
            }
            for(int i=0; i<actual.length(); i++){
                respuestas++;
                respuesta.accumulate("respuesta"+respuestas, ser);
                JSONArray ip= servidores.getJSONArray((String)actual.get(i));
                respuesta.accumulate("valor"+respuestas,ip.get(0)+":"+ip.get(1));
           }
        }
        
        respuesta.accumulate("servicio", "listar");
        respuesta.accumulate("respuestas", respuestas);
        return respuesta.toString();
    }
    private String listarTodo(){
        JSONObject respuesta  = new JSONObject();
        Iterator llaves = servicios.keys();
        int respuestas =0;
        while(llaves.hasNext()){
                String llaveA = (String)llaves.next();
                if(!llaveA.endsWith("P")){
                    
                    JSONArray actual= servicios.getJSONArray(llaveA);
                    for(int i=0; i<actual.length(); i++){
                        respuestas++;
                        respuesta.accumulate("respuesta"+respuestas, llaveA);
                        JSONArray ip= servidores.getJSONArray((String)actual.get(i));
                        respuesta.accumulate("valor"+respuestas,ip.get(0)+":"+ip.get(1));
                    }
                    
                }
        }
        respuesta.accumulate("servicio", "listar");
        respuesta.accumulate("respuestas", respuestas);
        return respuesta.toString();
    }
    
    public String ejecutar(JSONObject objeto){
        int variables = objeto.getInt("variables");
        String resultado = "{}";
        
        for(int i=0; i<variables; i++){
            String var = "variable"+ Integer.toString(i+1);
            String val = "valor"+ Integer.toString(i+1);
            String variable = objeto.getString(var);
            Object valor  = objeto.get(val);
            
            if("servicio".equals(variable)){
                JSONArray servidoresD;
                try{
                    servidoresD = servicios.getJSONArray((String)valor);
                }catch(JSONException js){
                    return "";
                }
                JSONArray servidoresAct = servidores.getJSONArray((String)servidoresD.get(0));
                String ip  = servidoresAct.getString(0);
                int port  = servidoresAct.getInt(1);
                ClienteS coneccion  = new ClienteS();
                try {
                    System.out.println(ip+":"+port);
                    System.out.println("Mensaje del server: "+parseEjecutarServer(objeto));
                   resultado = coneccion.mensajear(ip, port, parseEjecutarServer(objeto));
                   System.out.println("Respuesta del server: "+resultado);

                   JSONObject envio= new JSONObject(resultado);
                   System.out.println("Envio al cliente: "+parseEjecutarCliente(envio));
                   return parseEjecutarCliente(envio);
                } catch (IOException ex) {
                   resultado = "{\"error\":1}";
                   return resultado;
                } 
            }
        }
        return resultado;
    }
    
    public String parseEjecutarServer(JSONObject mensaje){
        JSONObject respuesta = new JSONObject();
        int variables = mensaje.getInt("variables");
        respuesta.put("variables", variables-1);
        int j=1;
        for(int i=0; i<variables; i++){
            String var = "variable"+ Integer.toString(i+1);
            String val = "valor"+ Integer.toString(i+1);
            String variable = mensaje.getString(var);
            Object valor  = mensaje.get(val);
            if("servicio".equals(variable)){
                respuesta.put("servicio", (String)valor);
                
            }else{
                respuesta.put("variable"+j, variable);
                respuesta.put("valor"+j, valor);
                j++;
            }
            
        }
        return respuesta.toString();
    }
    public String parseEjecutarCliente(JSONObject mensaje){
        JSONObject respuesta = new JSONObject();
        int variables = mensaje.getInt("respuestas");
        respuesta.put("servicio", "ejecutar");
        respuesta.put("respuestas", variables+1);
        respuesta.put("respuesta1", "servicio");
        respuesta.put("valor1", mensaje.getString("servicio"));

        for(int i=0; i<variables; i++){
            String var = "respuesta"+ Integer.toString(i+1);
            String val = "valor"+ Integer.toString(i+1);
            String respustaA = mensaje.getString(var);
            Object valor  = mensaje.get(val);
            respuesta.put("respuesta"+(i+2), respustaA);
            respuesta.put("valor"+(i+2), valor); 
        }
        return respuesta.toString();
    }
}
