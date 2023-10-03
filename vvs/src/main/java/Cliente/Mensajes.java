/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import org.json.JSONObject;

/**
 *
 * @author David Pat
 */
public class Mensajes {

    public static String listar(String servicio){
        JSONObject mensaje = new JSONObject();
        mensaje.put("servicio", "listar");
        mensaje.put("variables", 1);
        mensaje.put("variable1", "palabra");
        mensaje.put("valor1", servicio);
        return mensaje.toString();
    }
    public static String listar(){
        JSONObject mensaje = new JSONObject();
        mensaje.put("servicio", "listar");
        mensaje.put("variables", 0);
        return mensaje.toString();
    }
    
    public static String contar(){
        JSONObject mensaje = new JSONObject();
        mensaje.put("servicio", "ejecutar");
        mensaje.put("variables", 1);
        mensaje.put("variable1", "servicio");
        mensaje.put("valor1", "contar");
        return mensaje.toString();
    }
    public static JSONObject contar2(){
        JSONObject mensaje = new JSONObject();
        mensaje.put("servicio", "contar");
        mensaje.put("variables", 0);
        return mensaje;
    }
    public static String votar(String candidato){
        JSONObject mensaje=new JSONObject();
        mensaje.put("servicio", "ejecutar");
        mensaje.put("variables",2);
        mensaje.put("variable1","servicio");
        mensaje.put("valor1","votar");
        mensaje.put("variable2", candidato);
        mensaje.put("valor2", 1);
        return mensaje.toString();
    }
    public static JSONObject votar2(String candidato){
        JSONObject mensaje=new JSONObject();
        mensaje.put("servicio", "votar");
        mensaje.put("variables",1);
        mensaje.put("variable1",candidato);
        mensaje.put("valor1",1);
        return mensaje;
    }
    public static String registrar(String evento, String fecha){
        JSONObject mensaje=new JSONObject();
        mensaje.put("servicio", "ejecutar");
        mensaje.put("variables",3);
        mensaje.put("variable1","servicio");
        mensaje.put("valor1","registrar");
        mensaje.put("variable2", "evento");
        mensaje.put("valor2", evento);
        mensaje.put("variable3", "fecha");
        mensaje.put("valor3", fecha);
        return mensaje.toString();
    }
    public static JSONObject registrarPrueba(String evento, String fecha){
        JSONObject mensaje=new JSONObject();
        mensaje.put("servicio", "registrar");
        mensaje.put("variables",2);
        mensaje.put("variable1","evento");
        mensaje.put("valor1",evento);
        mensaje.put("variable2","fecha");
        mensaje.put("valor2",fecha);
        return mensaje;
    }

    public static String listarArchBit(){
        JSONObject mensaje = new JSONObject();
        mensaje.put("servicio", "ejecutar");
        mensaje.put("variables", 1);
        mensaje.put("variable1", "servicio");
        mensaje.put("valor1", "listar");
        return mensaje.toString();
    }

    public static JSONObject listarPrueba(){
        JSONObject mensaje=new JSONObject();
        mensaje.put("servicio", "ejecutar");
        mensaje.put("variables",0);
        
        return mensaje;
    }

}
