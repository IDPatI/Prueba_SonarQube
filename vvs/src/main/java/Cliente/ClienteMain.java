/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Controlador.controladorVotos;
import Modelo.actualizableImp;
import Vista.VistaListar;
import Vista.vistaDocumentoPlano;
import Vista.vistaGraficaBarras;
import Vista.vistaGraficaPastel;
import Vista.vistaPrincipal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.Port;

/**
 *
 * @author EQUIPO 1
 */
public class ClienteMain {
    public static ClienteB broker;
    public static String ipBroker;
    public static int portBroker;
    public static void main(String[] args) {
        broker = new ClienteB();
        String argumento = args[0];
        String[] partir = argumento.split(":");
        ipBroker = partir[0];
        portBroker = Integer.parseInt(partir[1]);
        //portBroker = 3434;
        try{
            broker.startConnection(ipBroker, portBroker);
        }catch(IOException ex){
            System.out.println("No se pudo conectar al broker");
        }
        actualizableImp actualizable = new actualizableImp(3);
        vistaPrincipal VistaPrincipal = new vistaPrincipal();
        VistaListar listar = new VistaListar();
        vistaGraficaPastel pastel = new vistaGraficaPastel();
        vistaGraficaBarras barras = new vistaGraficaBarras();
        
        controladorVotos control = new controladorVotos(actualizable, VistaPrincipal,listar, pastel, barras);
        VistaPrincipal.setVisible(true);
        //vistaDoc.setVisible(false);
        pastel.setVisible(true);
        barras.setVisible(true);
        listar.setVisible(true);
    }
}
