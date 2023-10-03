package Server.ModeloServer;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;
import Server.ModeloServer.Actualizable;

/**
 *
 * @author isaac
 */


public class actualizableImpServer implements Actualizable{
    private ArrayList<Producto> productos=new ArrayList<>();
    private ManipuladorDocs documento = new ManipuladorDocs("archivoCandidatos.txt");
    private ManipuladorDocs bitacora = new ManipuladorDocs("bitacora.txt");
    private int[] votos;
    String[] arrayCand;
        int[] arrayVotos;
    public actualizableImpServer() {
        arrayCand  = documento.obtenerCandidatosDoc();
        arrayVotos = documento.obtenerVotosDoc();
        
        
        for(int i=0;i<arrayCand.length;i++){
            productos.add(new Producto(arrayVotos[i], arrayCand[i]));
            System.out.println(productos.get(i).toString());
        }
    }
    
    public int[]obtenerInfoCandidatos(){
        return documento.obtenerVotosDoc();
    }
    
    /*public String[][] getTextoCand(){
        String[][] arrayArch=documento.leerArchivo();
        return arrayArch;
    }*/

    public void guardarInfoCandidatosa(){
        documento.escribirArchivo(productos);
    }

    public int getProductoVotos(){
        return 0;
    }
    
    @Override
    public void actualizarInformacion() {
        guardarInfoCandidatosa();
    }

    public JSONObject votarJSON(JSONObject solicitud){
        JSONObject voteJSON=new JSONObject();
        String[] arrayArch=documento.obtenerCandidatosDoc();
        boolean existe=false;
        voteJSON.accumulate("servicio", "votar");
        voteJSON.accumulate("respuestas", "1");
        /*for(int i=0;i<productos.size();i++){
            productos.get(i).toString();
        }*/
        for(int i=0;i<arrayArch.length;i++){//Checar si el elemento a votar esta en el archivo
            if(solicitud.getString("variable1").equals(arrayArch[i])){
                existe=true;
                productos.get(i).aumentarVotos();
                voteJSON.accumulate("respuesta1", solicitud.getString("variable1"));
                voteJSON.accumulate("valor1", ""+productos.get(i).getVotos());
                documento.escribirArchivo(productos);
            }
        }
        if(!existe){
            productos.add(new Producto(1, solicitud.getString("variable1")));
            voteJSON.accumulate("respuesta1", solicitud.getString("variable1"));
            voteJSON.accumulate("valor1", "1");
            documento.escribirArchivo(productos);
        }
        
        
        return voteJSON;
    }

    public JSONObject listarJSONVotos(){
        ArrayList<String> arrayBit=bitacora.getContenidoBitacora();
        JSONObject listJSON=new JSONObject();
        listJSON.accumulate("servicio", "listar");
        listJSON.accumulate("respuestas",""+arrayBit.size());
        int j=1;
        for(int i=0; i<arrayBit.size();i++){
            listJSON.accumulate("respuesta"+j, "evento");
            listJSON.accumulate("valor"+j, arrayBit.get(i).toString());
            j++;
        }
        return listJSON;
    }

    public JSONObject contarObjBitacora(){
        JSONObject candJSON=new JSONObject();
        candJSON.accumulate("servicio", "contar");
        candJSON.accumulate("respuestas", ""+productos.size());
        int j=1;
        for(int i=0;i<productos.size();i++){//Checar si el elemento a votar esta en el archivo
            candJSON.accumulate("respuesta"+j, productos.get(i).getNombreProducto());
            candJSON.accumulate("valor"+j, ""+productos.get(i).getVotos());
            j++;
        }
        
        return candJSON;
    }

    public JSONObject registroJSONBitacora(JSONObject solicitud){
        String evento=solicitud.getString("valor1");
        String fecha=solicitud.getString("valor2");
        bitacora.escribirBitacora(evento, fecha);
        ArrayList <String> arrayBit=bitacora.getContenidoBitacora();
        JSONObject bitJSON=new JSONObject();
        bitJSON.accumulate("servicio", "registrar");
        bitJSON.accumulate("respuestas", "1");
        bitJSON.accumulate("respuesta1", "eventos");
        bitJSON.accumulate("valor1", ""+arrayBit.size());
        return bitJSON;
    }
}
