











package Server.ModeloServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isaac
 */
public class ManipuladorDocs {
    private Scanner fileScanner;
    private String rutaArch;
    private ArrayList<String> lineasArchivo;
    
    public ManipuladorDocs(String path){
        try {
            fileScanner = new Scanner(new File(path));
            this.rutaArch=path;
            this.lineasArchivo=new ArrayList<String>();
            this.lineasArchivo=getContentFile(path);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
    public ArrayList<String> getContentFile(String path){
        FileReader in;
        try {
            in = new FileReader(path);
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                lineasArchivo.add(line);
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        
        
        return lineasArchivo;
    }
    
    public ArrayList<String> getArrayLCand(){
        return lineasArchivo;
    }
    
    public int[] obtenerVotosDoc(){
        ArrayList<String> content =lineasArchivo;
        int[] votos=new int[content.size()];
        //System.out.println(content.size());
        for(int i=0;i<content.size();i++){
            String[] columnas=content.get(i).split(",");
            //System.out.println(columnas[1]);
            votos[i]=Integer.parseInt(columnas[1]);
        }
        return votos;
    }
    public String[] obtenerCandidatosDoc(){
        ArrayList<String> content = lineasArchivo;
        String[] candidatos=new String[content.size()];
        for(int i=0;i<content.size();i++){
            String[] columnas=content.get(i).split(",");
            //System.out.println(columnas[0]);
            candidatos[i]=columnas[0].trim();
        }
        return candidatos;
    }
    
    public void escribirArchivo(ArrayList<Producto> candidatosVotos){
        PrintWriter fileOut;
        /*for(int i=0;i<candidatosVotos.size();i++){
            candidatosVotos.get(i).toString();
        }*/
        try{
            fileOut = new PrintWriter(new FileWriter("archivoCandidatos.txt",false));
            for(int i = 0; i< candidatosVotos.size();i++){
                if(i==0){
                    fileOut.print(candidatosVotos.get(i).getNombreProducto()+","+candidatosVotos.get(i).getVotos());
                }else{
                    fileOut.print("\n"+candidatosVotos.get(i).getNombreProducto()+","+candidatosVotos.get(i).getVotos());
                }
            }
            fileOut.close();
        }catch(FileNotFoundException e){
            System.out.println("Error: "+ e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ManipuladorDocs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void escribirBitacora(String evento, String fecha){
        PrintWriter fileOut;
        ArrayList<String> content = new ArrayList<String>();
        content=getContenidoBitacora();
        content.add(fecha +" "+evento);
        try{
            fileOut = new PrintWriter(new FileWriter("bitacora.txt",false));
            for(int i=0;i<content.size();i++){
                if(i==0){
                    fileOut.print(content.get(i).toString());
                }else{
                    fileOut.print("\n"+content.get(i).toString());
                }
            }
            fileOut.close();
        }catch(FileNotFoundException e){
            System.out.println("Error: "+ e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ManipuladorDocs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getContenidoBitacora(){
        ArrayList<String> content = new ArrayList<String>();
        try {
            Scanner scanBit = new Scanner(new File(rutaArch));
            while (scanBit.hasNextLine()) {
                content.add(scanBit.nextLine());
            }
            scanBit.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           
           
       return content;
   }
    
}
