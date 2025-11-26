package intento1;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeerFicheros {

    final static Path archivoTXT = Path.of("lista_alumnado_DAM2.txt");
    final static Path archivoCSV = Path.of("lista_alumnado_DAW1.csv");


    public static List<Alumno> ficheroTXT() {

        List<Alumno> alumnos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(archivoTXT, StandardCharsets.UTF_8)){

            String linea;

            while ((linea = reader.readLine()) != null)
            {
                linea = linea.trim();

                String[] partes = linea.split(",");

                if (partes.length == 5)
                {
                    String nombre = partes[2].trim();
                    String apellido = partes[1].trim();
                    String ciudad = partes[3].trim();
                    int edad = Integer.parseInt(partes[4].trim());

                    if (ciudad.equalsIgnoreCase("cartagena"))
                    {
                        Alumno a = new Alumno(apellido, nombre, ciudad, edad, LocalDate.now());
                        alumnos.add(a);
                    }
                }
            }
        }
        catch(Exception e){
            System.err.println("Error al leer fichero " + archivoTXT.getFileName());
        } return alumnos;
    }

    public static List<Alumno> ficheroCSV() {

        List<Alumno> alumnos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(archivoCSV, StandardCharsets.UTF_8))
        {
            String linea;

            while ((linea = reader.readLine()) != null) {

                linea = linea.trim();

                String[] partes = linea.split(";");

                if (partes.length == 5) {

                    String nombre = partes[1].trim();
                    String apellido = partes[2].trim();
                    String ciudad = partes[3].trim();
                    int edad = Integer.parseInt(partes[4].trim());

                    if (ciudad.equalsIgnoreCase("cartagena")){
                        Alumno a = new Alumno(apellido, nombre, ciudad, edad, LocalDate.now());

                        alumnos.add(a);
                    }
                }
            }
        }catch(Exception e){
            System.err.println("Error al leer fichero " + archivoCSV.getFileName());
        }
        return alumnos;
    }
}
