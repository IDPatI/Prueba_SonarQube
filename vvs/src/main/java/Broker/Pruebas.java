/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;
import java.io.IOException;

import javax.sound.midi.spi.MidiFileWriter;

import org.json.JSONObject;

import Cliente.ClienteB;
import Cliente.Mensajes;
/**
 *
 * @author EQUIPO 1
 */
public class Pruebas {
    public static void main(String[] args) {
        ClienteB broker  =new ClienteB();
        try{
            broker.startConnection("127.0.0.1", 3434);
        }catch(IOException ex){
            System.out.println("No se pudo conectar al broker");
        }
        try {
            String votar = broker.sendMessage(Mensajes.contar());
            System.out.println(votar);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
