package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Producto;

/**
 *
 * @author isaac
 */
public class ManipuladorDocs {
    private Scanner fileScanner;
    
    public ManipuladorDocs(String path){
        try {
            fileScanner = new Scanner(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
    
    public String[][] leerArchivo(){
        String[] parsing = getContentFile();
        String[][] candidatosVotos = new String[parsing.length][2];
        for(int i = 0; i<parsing.length; i++){
            String[] parsing2 = parsing[i].split(",");
            System.arraycopy(parsing2, 0, candidatosVotos[i], 0, 2);
        }
        return candidatosVotos;
    }
    
    public String[] getContentFile(){
         String[] content = new String[3];
         int i = 0;
            while (fileScanner.hasNextLine()) {
                content[i]=fileScanner.nextLine()+"\n";
                i++;
            }
            fileScanner.close();
        return content;
    }
  
    public void closeFile(){
        fileScanner.close();
    }
    
    /*public void escribirArchivo(Producto[] candidatosVotos){
        PrintWriter fileOut;
        try{
            fileOut = new PrintWriter(new FileWriter("archivoCandidatos.txt",false));
            for(int i = 0; i< candidatosVotos.length;i++){
                fileOut.println(candidatosVotos[i]);
            }
            fileOut.close();
        }catch(FileNotFoundException e){
            System.out.println("Error: "+ e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ManipuladorDocs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
}
