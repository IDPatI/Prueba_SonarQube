/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

import java.net.Socket;

/**
 *
 * @author David Pat
 */
public class BrokerMain {
    public static void main(String[] args) {
        String puerto = args[0];
        Broker broker = new Broker();
        broker.empezar(puerto);
        
    }
    
}
