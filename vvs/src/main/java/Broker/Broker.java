/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

import java.io.IOException;
import java.net.ServerSocket;
/**
 *
 * @author David Pat
 */
public class Broker {
    private ServerSocket broker;
    public static Servicios s;
    public void empezar(String port){
        s = new Servicios();
        try {
            broker = new ServerSocket(Integer.parseInt(port));
            System.out.println("Broker iniciado:"+ port);
            while(true){
                new BrokerThreads(broker.accept()).start();
            }
        } catch (IOException ex) {
            System.out.println("Falla al Empezar: "+ ex.getMessage());
        }
    }
}
