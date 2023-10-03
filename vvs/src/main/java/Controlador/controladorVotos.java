package Controlador;

import Modelo.PeticionesListar;
import Modelo.Producto;
import Modelo.actualizableImp;
import Vista.VistaListar;
import Vista.vistaDocumentoPlano;
import Vista.vistaGraficaBarras;
import Vista.vistaGraficaPastel;
import Vista.vistaPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author isaac
 */
public class controladorVotos implements ActionListener{
    private actualizableImp actualizable;
    private vistaPrincipal VistaPrincipal;
    //private vistaDocumentoPlano vistaDoc;
    private Vista.vistaGraficaPastel vistaGraficaPastel;
    private vistaGraficaBarras vistaGraficabarras;
    private VistaListar reportes;

    public controladorVotos(actualizableImp actualizable, vistaPrincipal VistaPrincipal, VistaListar reportes, vistaGraficaPastel vistaPastel, vistaGraficaBarras barras) {
        this.actualizable = actualizable;
        this.VistaPrincipal = VistaPrincipal;
        this.reportes = reportes;
        //this.vistaDoc = vistaDoc;
        this.vistaGraficaPastel = vistaPastel;
        this.vistaGraficabarras = barras;

    
        double xvp =VistaPrincipal.getLocation().getX() ;
        double yvp =VistaPrincipal.getLocation().getY();
        reportes.setLocation((int)xvp +VistaPrincipal.getWidth() ,(int)yvp);

        VistaPrincipal.setProducto1(actualizable.getProducto()[0].getNombreProducto());
        VistaPrincipal.setProducto2(actualizable.getProducto()[1].getNombreProducto());
        VistaPrincipal.setProducto3(actualizable.getProducto()[2].getNombreProducto());

        this.VistaPrincipal.getjButton1().addActionListener(this);
        this.VistaPrincipal.getjButton2().addActionListener(this);
        this.VistaPrincipal.getjButton3().addActionListener(this);

        this.reportes.getBuscar().addActionListener(this);
            generarGraficaDePastel();
            generarGraficaDeBarras();
            iniciarListaServicios();
            actualizarBitacora();
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(VistaPrincipal.getjButton1()== evento.getSource()){
            try {
                actualizable.actualizarProductoUno();
            } catch (Exception e) {;
                setWarningMsg(e.getMessage());
            }
            //vistaDoc.getjTable1().setValueAt(actualizable.getProductoUnoVotos(), 0, 0);
            generarGraficaDePastel();
            generarGraficaDeBarras();
            
        }
        if(VistaPrincipal.getjButton2()== evento.getSource()){
            try {
                actualizable.actualizarProductoDos();
            } catch (Exception e) {
                setWarningMsg(e.getMessage());
            }
            //vistaDoc.getjTable1().setValueAt(actualizable.getProductoDosVotos(), 0, 1);
            generarGraficaDePastel();
            generarGraficaDeBarras();
        }
        if(VistaPrincipal.getjButton3()== evento.getSource()){
            try {
                actualizable.actualizarProductoTres();
            } catch (Exception e) {
                setWarningMsg(e.getMessage());
            }
            //vistaDoc.getjTable1().setValueAt(actualizable.getProductoTresVotos(), 0, 2);
            generarGraficaDePastel();
            generarGraficaDeBarras();
        }
        if(reportes.getBuscar() == evento.getSource()){
            buscarServicioBroker();
        }
        actualizarBitacora();
           
    }
    
    public void generarGraficaDePastel(){
        Producto[] product = new Producto[3];
        product = actualizable.getProducto();
        
        DefaultPieDataset datosPie = new DefaultPieDataset();
        datosPie.setValue(product[0].getNombreProducto(), product[0].getVotos());
        datosPie.setValue(product[1].getNombreProducto(), product[1].getVotos());
        datosPie.setValue(product[2].getNombreProducto(), product[2].getVotos());
        JFreeChart grafico = ChartFactory.createPieChart("Grafica pastel", datosPie,true ,true, false);
        ChartPanel cPanel = new ChartPanel(grafico);
        vistaGraficaPastel.getjPanel1().removeAll();
        vistaGraficaPastel.getjPanel1().add(cPanel,BorderLayout.NORTH);
        vistaGraficaPastel.getjPanel1().validate();
    }
    
    public void generarGraficaDeBarras(){
        Producto[] product = new Producto[3];
        product = actualizable.getProducto();
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        datos.addValue(product[0].getVotos(), "Grafica 1", product[0].getNombreProducto());
        datos.addValue(product[1].getVotos(), "Grafica 1", product[1].getNombreProducto());
        datos.addValue(product[2].getVotos(), "Grafica 1", product[2].getNombreProducto());
        JFreeChart grafico = ChartFactory.createBarChart("Grafica barras","Eje x", "Eje y", datos,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel cPanel = new ChartPanel(grafico);
        
        vistaGraficabarras.getjPanel2().removeAll();
        vistaGraficabarras.getjPanel2().add(cPanel);
        vistaGraficabarras.getjPanel2().validate();
    }

    public void iniciarListaServicios(){
        String iniciar = PeticionesListar.listarServiciosBroker();
        reportes.setTextoRusltado(iniciar);
        System.out.println("Entré aqui");
    }

    public void buscarServicioBroker(){
        String busquesa = reportes.textFieldServicio();
        if(busquesa.equals("")){
            iniciarListaServicios();
        }else{
            reportes.setTextoRusltado(PeticionesListar.buscarServiciosBroker(busquesa));
        }

    }

    public void actualizarBitacora(){
        String bitacora  = PeticionesListar.ActualizarBitacora();
        reportes.setTextoBitacora(bitacora);
    }
    public static void setWarningMsg(String text){
        JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
