package Modelo;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import Cliente.ClienteMain;
import Cliente.Mensajes;
import Controlador.controladorVotos;
/**
 *
 * @author isaac
 */
import Modelo.ManipuladorDocs;
public class actualizableImp implements Actualizable{
    private Producto[] productos;
    private ManipuladorDocs documento = new ManipuladorDocs("archivoCandidatos.txt");

    public actualizableImp(int cantidadCandidatos) {
        this.productos = new Producto[cantidadCandidatos];
        this.productos[0] = new Producto(0,"Xbox");
        this.productos[1] = new Producto(0,"Pc");
        this.productos[2] = new Producto(0,"Nintendo");
        actualizarInformacion();
    }
    
    public void guardarInfoCandidatosa(){
        //documento.escribirArchivo(productos);
    }

    public Producto[] getProducto() {
        return productos;
    }
    
    public void actualizarProductoUno() throws Exception{
        actulizarProducto(0);
    }
    
    public int getProductoUnoVotos(){
        return productos[0].getVotos();
    }
    
    public void actualizarProductoDos() throws Exception{
        actulizarProducto(1);
    }
    
    public int getProductoDosVotos(){
        return productos[1].getVotos();
    }
    
    public void actualizarProductoTres() throws Exception{
        actulizarProducto(2);
    }
    
    public int getProductoTresVotos(){
        return productos[2].getVotos();
    }
    
    @Override
    public void actualizarInformacion() {
        String serviciosString;
        try {
            serviciosString = ClienteMain.broker.sendMessage(Mensajes.contar());
            PeticionesListar.registrarEvento("Contar todos los votos");
        } catch (IOException e) {
            controladorVotos.setWarningMsg("Ocurrio un error al intentar cargar la informacion de los votos");
            return;
        }
        JSONObject respuesta = new JSONObject(serviciosString);
        JSONObject productos = new JSONObject();
        int cantRes = respuesta.getInt("respuestas");
        for(int i= 1; i< cantRes; i++){
            String producto = respuesta.getString("respuesta"+ (i+1));
            String val = respuesta.getString("valor"+ (i+1));
            productos.put(producto, val);
        }

        String nom1 = this.productos[0].getNombreProducto();
        String nom2 = this.productos[1].getNombreProducto();
        String nom3 = this.productos[2].getNombreProducto();

        try{
            int voto= Integer.parseInt(productos.getString(nom1));
            this.productos[0].setVotos(voto);
        }catch(JSONException js){
            this.productos[0].setVotos(0);
        }
        try{
            int voto = Integer.parseInt(productos.getString(nom2));
            this.productos[1].setVotos(voto);
        }catch(JSONException js){
            this.productos[1].setVotos(0);
        }
        try{
            int voto = Integer.parseInt(productos.getString(nom3));
            this.productos[2].setVotos(voto);
        }catch(JSONException js){
            this.productos[2].setVotos(0);
        }
    }

    public void actulizarProducto(int i) throws Exception{
        String nomP = productos[i].getNombreProducto();
        String serviciosString;
        serviciosString = ClienteMain.broker.sendMessage(Mensajes.votar(nomP));
        PeticionesListar.registrarEvento("Registar voto para"+ nomP);
        if(serviciosString.equals("")){
            throw new Exception("El serviocio no está registrado");
        }
        JSONObject respuesta = new JSONObject(serviciosString);
        if(respuesta.getString("respuesta2").equals(nomP)){
            String val = respuesta.getString("valor2");
            int votos = Integer.parseInt(val);
            productos[i].setVotos(votos);
        }
    }





}
