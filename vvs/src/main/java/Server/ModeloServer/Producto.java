package Server.ModeloServer;

/**
 *
 * @author isaac
 */
public class Producto {
    private int cantidadVotos;
    private String nombreProducto;
    
    @Override
    public String toString(){
        return nombreProducto+","+cantidadVotos;
    }
    public Producto(int votos, String nombreCandidato) {
        this.cantidadVotos = votos;
        this.nombreProducto = nombreCandidato;
    }
    
    public void aumentarVotos(){
        this.cantidadVotos+=1;
    }

    public int getVotos() {
        return cantidadVotos;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
     
}
