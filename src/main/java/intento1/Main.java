package intento1;

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Path dir = Paths.get("salida");
    static Path rutaBinario = Path.of("salida", "cursos.dat");
    static Path rutaJSON = Path.of("salida", "curso.json");
    static Path rutaXML = Path.of("salida", "curso.xml");

    public static void main(String[] args) {

        List<Alumno> dam = LeerFicheros.ficheroTXT();
        List<Alumno> daw = LeerFicheros.ficheroCSV();

        Curso dam2 = new Curso("DAM1", dam);
        Curso daw1 = new Curso("DAM2", daw);

        List<Curso> cursos = new ArrayList<>();
        cursos.add(dam2);
        cursos.add(daw1);

        //Mostrar por pantalla los alumnos de cada curso
        try{
            System.out.println("===Lista de alumnos de DAW1===");
            for (Alumno alumno : daw1.getAlumnos()) {
                System.out.println(alumno);
            }

            System.out.println("===Lista de alumnos de DAM2===");
            for (Alumno alumno : dam2.getAlumnos()) {
                System.out.println(alumno);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        escribirBinario(rutaBinario, cursos );
        leerBinario(rutaBinario);
        escribirJSON(rutaJSON, cursos);
        leerJSON(rutaJSON);
        escribirXML(rutaXML, cursos);


    }

     static void escribirBinario(Path ruta, Object o) {

        try{
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
        }catch(Exception e){
            System.err.println("Error al escribir en binario:");
        }

        try(var writer = new ObjectOutputStream(Files.newOutputStream(ruta)))
        {
            writer.writeObject(o);
            System.out.println("===Archivo guardado en binario===" );

        }catch(Exception e){
            throw new RuntimeException(e);
        }
     }

     static void leerBinario(Path ruta) {
        try(var reader = new ObjectInputStream(Files.newInputStream(ruta)))
        {
            @SuppressWarnings("unchecked")
            var lista = (List<Curso>) reader.readObject();


            System.out.println("===BINARIO LEIDO===");

            for (Curso c : lista) {
                System.out.println("Curso: " + c.getNombre() + " Alumnos: " + c.getAlumnos().size());

                for (Alumno alumno : c.getAlumnos()) {
                    System.out.println("Nombre: " + alumno.getNombre() + " " + alumno.getApellido() + ". Años: " + alumno.getEdad() + " | " + alumno.getCiudad());
                }
            }

        } catch (Exception e) {
            System.err.println("Error al leer en binario");
        }
     }

     static void escribirJSON(Path ruta, Object o) {

        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
        }catch(Exception e){
            System.err.println("Error al crear la carpeta:");
        }


        try(var writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8))
        {
            var mapper = JsonMapper
                    .builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();

            mapper.writeValue(writer, o);

        } catch (Exception e) {
            System.err.println("Error al ESCRIBIR en JSON");
        }
     }

     static void leerJSON(Path ruta) {
        try(var reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8))
        {
            var mapper = JsonMapper
                    .builder()
                    .build();

            Curso [] cursos = mapper.readValue(reader, Curso[].class);

            System.out.println("===JSON LEIDO===");

            for (Curso c : cursos) {
                System.out.println("Curso: " + c.getNombre() + " Alumnos: " + c.getAlumnos().size());
                for (Alumno alumno : c.getAlumnos()) {
                    System.out.println("Nombre: " + alumno.getNombre() + " " + alumno.getApellido() + ". Años: " + alumno.getEdad() + " | " + alumno.getCiudad());
                }
            }

        }catch(Exception e){
            System.err.println("Error al LEER en JSON");
        }
     }

     static void escribirXML(Path ruta, Object o) {
        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(var writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8))
        {
            var mapper = XmlMapper
                    .builder()
                  //  .defaultUseWrapper(false)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();

            mapper
                    .writer().withRootName("Cursos")
                    .writeValue(writer, o);

        } catch (Exception e) {
            System.err.println("Error al escribir en XML:");
        }
     }


}
