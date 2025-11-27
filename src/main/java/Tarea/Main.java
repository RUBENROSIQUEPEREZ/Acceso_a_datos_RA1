package Tarea;
import Tarea.vo.Alumno;
import Tarea.vo.Curso;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Path txtDAM2 = Path.of("src", "main", "java", "Tarea", "recursos","lista_alumnado_DAM2.txt");

    static Path csvDAW1 = Path.of("src", "main", "java", "Tarea", "recursos","lista_alumnado_DAW1.csv");

    static Path dir = Path.of("src", "main", "java", "Tarea", "salida");

    static Path dat = Path.of("src", "main", "java", "Tarea", "salida", "cursos.dat");

    static Path json = Path.of("src", "main", "java", "Tarea", "salida", "cursos.json");

    static Path xml = Path.of("src", "main", "java", "Tarea", "salida", "cursos.xml");

    public static void main(String[] args) throws Exception {

        //Hay que add, throws Exception
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }

        //Creo dos listas con los alumnos de cada archivo
        List <Alumno> DAM = leer_archivos(txtDAM2);
        List <Alumno> DAW = leer_archivos(csvDAW1);


        //Meto los dos archivos en una lista unica de cursos
        List<Curso> cursos = List.of(new Curso("DAM", DAM),
                                    new Curso("DAW", DAW));

        System.out.println("===ARCHIVO LEIDO DESDE EL TEXTO PLANO ===");

        for (Curso c : cursos) {
            System.out.println("***"+c.getNombre()+"***");
            for (Alumno a : c.getAlumnos()) {
                System.out.println(a.getNombre());
            }
        }

        writeBinario(dat, cursos);
        readBinario(dat);
        writeJSON(json, cursos);
        readJSON(json);
        writeXML(xml, cursos);
        readXML(xml);



    }

    static List<Alumno> leer_archivos(Path archivo){

        List<Alumno> alumnos = new ArrayList<>();
        try(var reader = Files.newBufferedReader(archivo)){

            String linea;

            String separador = "";

            String name = archivo.getFileName().toString();

            if (name.contains("DAM2")){
                separador = ",";
            }else if(name.contains("DAW1")){
                separador = ";";
            }

            while((linea = reader.readLine()) != null){

                linea = linea.trim();

                if (linea.isEmpty())continue;

                String [] partes = linea.split(separador.trim());

                if (partes.length == 5){
                    String id = partes[0].trim();
                    String apellidos = partes[1].trim();
                    String nombre =  partes[2].trim();
                    String ciudad = partes[3].trim();
                    int edad = Integer.parseInt(partes[4].trim());

                    if (ciudad.equalsIgnoreCase("cartagena")){
                        Alumno alumno = new Alumno(id ,apellidos, nombre, ciudad, edad, LocalDate.now());
                        alumnos.add(alumno);
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("Error al leer archivo " + archivo);

        }
        return alumnos;
    }
    //Implementar en las clases con las que este implicado, el Serializable, si no lo lleva no funciona
    public static void writeBinario (Path binario, Object o){

        try(var writer = new ObjectOutputStream(Files.newOutputStream(binario))){
            writer.writeObject(o);
            System.out.println("Archivo guardado en binario " + binario);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo en binario " + e.getMessage() + e);
        }
    }

    public static void readBinario (Path ruta){
        try(var reader = new ObjectInputStream(Files.newInputStream(ruta)))
        {
            @SuppressWarnings("unchecked") //Segun el tema
            var lista = (List<Curso>) reader.readObject();

            System.out.println("=====Archivo leido desde " + ruta.getFileName() + "=====");

            for (Curso c : lista) {
                System.out.println("Nombre del curso: " + c.getNombre());
                for (Alumno a : c.getAlumnos()) {
                    System.out.println("Nombre: " + a.getNombre());
                }
            }

        }catch (Exception ex){
            System.out.println("Error al leer el archivo " + ex.getMessage());
        }
    }

    public static void writeJSON (Path ruta, Object o){
        try(var writer = new BufferedWriter(Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)))
        {
            var mapper = JsonMapper
                    .builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();

            mapper.writeValue(writer, o);

            System.out.println("JSON guardado en  " + ruta);
        }catch (Exception ex){
            System.out.println("Error al guardar el archivo " + ex.getMessage());
        }
    }

    public static void readJSON (Path ruta){
        try(var reader = new BufferedReader(Files.newBufferedReader(ruta, StandardCharsets.UTF_8)))
        {
            var mapper = JsonMapper.builder().build();

            System.out.println("=====Archivo leido desde " + ruta.getFileName() + "=====");

            //1era forma de hacerlo
//            var tipo = Curso[].class;
//            Curso[] cursos = mapper.readValue(reader,tipo);

            //2da forma de hacerlo
            Curso [] cursos = mapper.readValue(reader,Curso[].class);

            for (Curso c : cursos) {
                System.out.println("Nombre del curso: " + c.getNombre());
                for (Alumno a : c.getAlumnos()) {
                    System.out.println("Nombre: " + a.getNombre());
                }
            }
        }catch (Exception ex){
            System.out.println("Error al leer el archivo " + ex.getMessage());
        }
    }

    public static void writeXML (Path ruta, Object o){
        try(var writer = new BufferedWriter(Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)))
        {

            var mapper = XmlMapper
                    .builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();


            mapper.writeValue(writer, o);

            System.out.println("Archivo guardado en  " + ruta);

        }catch (Exception ex){
            System.out.println("Error al guardar el archivo " + ex.getMessage());
        }
    }

    public static void readXML (Path ruta){
        try(var reader = new BufferedReader(Files.newBufferedReader(ruta, StandardCharsets.UTF_8)))
        {
            var mapper = XmlMapper.builder().build();

            Curso [] cursos = mapper.readValue(reader,Curso[].class);

            System.out.println("=====Archivo leido desde " + ruta.getFileName() + "=====");
            for (Curso c : cursos) {
                System.out.println("Nombre del curso: " + c.getNombre());
                for (Alumno a : c.getAlumnos()) {
                    System.out.println("Nombre: " + a.getNombre());
                }
            }
        }catch (Exception ex){
            System.out.println("Error al leer el archivo " + ex.getMessage());
        }
    }
}
