package Ejercicios.a1_2;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static Path txt = Path.of("src", "main", "java", "Ejercicios", "a1_2", "recursos","frases.txt");

    static Path txtv2 = Path.of("src", "main", "java", "Ejercicios", "a1_2", "procesados","frases_filtradas.txt");

    static Path dir = Path.of("src", "main", "java", "Ejercicios", "a1_2", "procesados");

    public static void main(String[] args) {


        try(var reader = Files.newBufferedReader(txt, StandardCharsets.UTF_8);
            var writer = Files.newBufferedWriter(txtv2, StandardCharsets.UTF_8)) {

            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] split = linea.split("\\|");

                if (split.length == 3){
                    String numero = split[0].trim();
                    String text = split[1].trim();
                    String autor = split[2].trim();

                    if (numero.startsWith("2")){

                        if (autor.endsWith("Monroe") || autor.endsWith("Davis")){
                            //El resultadao no muestra nada por las condiciones puestass
                            writer.write("'" + autor + "'" + " - " + text );
                            writer.newLine();
                        }
                    }
                }
            }

            //Mando a escribir para que se vea que el Main funciona para escribir en el archivo
            writer.write("FIN.");

            //Lo dejo comentado para no borrar el archivo
            //Este seria el ultimo paso para terminar la tarea
            //Files.delete(txt);
        }catch(Exception e){
            System.out.println("Error al abrir archivo " + e.getMessage());
        }
    }
}
