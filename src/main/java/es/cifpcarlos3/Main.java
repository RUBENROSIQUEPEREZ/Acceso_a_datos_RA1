package es.cifpcarlos3;

import es.cifpcarlos3.vo.Alumno;
import es.cifpcarlos3.vo.Curso;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //CREO LAS RUTAS PARA POSTERIORMENTE LEER ESTOS ARCHIVOS CON JAVA NIO
        Path base = Path.of("src", "main", "java", "es/cifpcarlos3");
        Path txt = base.resolve("lista_alumnado_DAM2.txt");
        Path csv = base.resolve("lista_alumnado_DAW1.csv");

        //CREO LAS RUTAS DONDE QUIERO QUE SE GUARDEN, CON LA EXTENSION AÑADIDA
        Path rutaBinario = Path.of("salida", "cursos.dat");
        Path rutaJSON = Path.of("salida", "curso.json");
        Path rutaXML = Path.of("salida", "curso.xml");

        List<Alumno> dam2 = leerFichero(txt, ",");
        List<Alumno> daw1 =leerFichero(csv, ";");

        System.out.println("Alumnos DAM2 leídos: " + dam2.size());
        System.out.println("Alumnos DAW1 leidos: " + daw1.size());

        Curso DAM = new Curso("DAM2", dam2);
        Curso DAW = new Curso("DAW1", daw1);

        List<Curso> cursos = new ArrayList<>();
        cursos.add(DAM);
        cursos.add(DAW);

        binarioguardar(rutaBinario, cursos);
        leerbinario(rutaBinario);

        guardarJSON(rutaJSON, cursos);
        leerJSON(rutaJSON);

        xmlguardar(rutaXML, cursos);
        leerXML(rutaXML);

    }

    //Creo un metodo donde se le pasa la de donde esta el fichero, mas el separador, ya que estos archivos tienen separadores diferentes
    public static List <Alumno> leerFichero(Path rutaFichero, String separador){

        //Creo una lista de alumnos, que es donde yo quiero que se guarden
        List <Alumno> alumnos = new ArrayList<>();

        //Desde su ruta leo el fichero, con el formato UTF_8
        try (BufferedReader bufferedReader = Files.newBufferedReader(rutaFichero, StandardCharsets.UTF_8))
        {
            //Almaceno la primera linea
            String linea;

            while ((linea = bufferedReader.readLine()) != null){

                if (linea.isBlank()){
                    continue;
                }

                //Divido las partes de la linea para guardarlas en variables
                String [] partes = linea.split(separador + "\\s*");

                if (partes.length == 5){
                    String apellidos = partes[1].trim();
                    String nombres = partes[2].trim();
                    String ciudad = partes [3].trim();
                    Integer edad = Integer.parseInt(partes[4].trim());

                    if (!ciudad.equalsIgnoreCase("Cartagena")){
                        continue;
                    }

                    //Creo el objeto alumno y le añado sus nuevas variables
                    Alumno alumno = new Alumno(nombres, apellidos, edad, LocalDate.now());

                    //Alumno los añano a la lista llamada alumnos
                    alumnos.add(alumno);
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());;
        }
        return alumnos;
    }

    public static void binarioguardar(Path ruta, Object o){

        try {
            Path carpeta = Paths.get("salida");

            if (Files.notExists(carpeta)) {
                Files.createDirectories(carpeta);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        //Al querer escribir en binario, hay que llamar a ObjectOutputStream
        try(var oos = new ObjectOutputStream(Files.newOutputStream(ruta))){
            oos.writeObject(o);
            System.out.println("Archivo guardado en binario " + ruta);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo en binario " + e.getMessage() + e);
        }
    }

    //Donde se nos generó el archivo .dat, hay que pasarlo a OIS y dentro de el FIS
    public static void leerbinario(Path rutabinaro){
        try(var ois = new ObjectInputStream(Files.newInputStream(rutabinaro)))
        {
            @SuppressWarnings("unchecked") //Segun el tema
            var lista = (List<Curso>) ois.readObject();

            System.out.println("===============" + "Archivo leido de DAT" + "===============");

            for (Curso c : lista){
                System.out.println("Curso: " + c.getNombre() + " Alumnos : " +c.getListaAlumnos().size());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void guardarJSON (Path ruta, List<Curso> listaCursos){
        try {
            Path carpeta = Paths.get("salida");
            if (Files.notExists(carpeta)) {
                Files.createDirectories(carpeta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Para escribir FBW, pasandole la ruta con el formato UTF_8
        try (var writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            var mapper = JsonMapper.builder() // Inicia la construccion del JSON
                    .enable(SerializationFeature.INDENT_OUTPUT) // salida JSON con formato
                    .build(); //Genera la instancia final

            mapper.writeValue(writer, listaCursos); // Le pasamos como parametros la ruta (writer) y lista de cursos

            System.out.println("Archivo  guardado en JSON " + ruta);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void leerJSON (Path ruta){
        try(var reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            var mapper = JsonMapper
                    .builder() //Incia JsonMapper
                    .build(); //Genera la instancia final de JSONMapper

            System.out.println("===============" + "Archivo leido de JSON" + "===============");

            Curso[] cursos = mapper.readValue(reader, Curso[].class);
            for (Curso c : cursos ){
                System.out.println("Curso: " + c.getNombre() +" Alumnos: " + c.getListaAlumnos().size());
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void xmlguardar(Path ruta, List<Curso> listaCursos){
        try {
            Path carpeta = Paths.get("salida");
            if (Files.notExists(carpeta)) {
                Files.createDirectories(carpeta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (var writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            var mapper = XmlMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();

            mapper.writer().withRootName("cursos")
                    .writeValue(writer, listaCursos);

            System.out.println("Archivo  guardado en XML " + ruta);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void leerXML (Path ruta){
        try(var reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            var mapper = XmlMapper.builder().build();

            System.out.println("===============" + "Archivo leido de XML" + "===============");
            Curso[] cursos = mapper.readValue(reader, Curso[].class);
            for (Curso c : cursos ){
                System.out.println("Curso: " + c.getNombre() +" Alumnos: " + c.getListaAlumnos().size());
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}


