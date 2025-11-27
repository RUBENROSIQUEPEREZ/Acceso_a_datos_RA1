package Ejercicios.a1_1;

import java.io.BufferedReader;
import java.io.FilterOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static Path txt = Path.of("src", "main", "java", "Ejercicios", "a1_1", "recursos","informacion_ciclos_1_1.txt");


    public static void main(String[] args) {

        try(var reader = Files.newBufferedReader(txt, StandardCharsets.UTF_8))
                //Tambien esta esta forma de leer los archivos de texto, es totalmente valido uno como otro
                //BufferedReader br = Files.newBufferedReader(txt, StandardCharsets.UTF_8);
        {
            String linea;

            while((linea = reader.readLine()) != null){

                if(linea.startsWith("#"))continue;

                linea = linea.trim();


                String [] partes = linea.split(",");

                if (partes.length == 4) {
                    String codigo = partes[0].trim();
                    String nombre = partes[1].trim();
                    int horas = Integer.parseInt(partes[2].trim());
                    String familia = partes[3].trim();

                    System.out.println("INSERT INTO T_CICLO VALUES (" + codigo + "," + nombre + "," + horas + "," + familia + ")");
                }
            }

        }catch(Exception e){
            System.err.println("Ruta erronea con el archivo " + txt.getFileName() + " o archivo defectuoso");
        }
    }
}
