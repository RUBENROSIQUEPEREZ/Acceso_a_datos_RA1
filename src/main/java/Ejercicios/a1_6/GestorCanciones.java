package Ejercicios.a1_6;

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import  Ejercicios.a1_6.vo.Cancion;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GestorCanciones {

    // Rutas relativas al paquete de la actividad
    private static final Path DIR_BASE = Path.of("src","main","java","Ejercicios","a1_6");
    private static final Path FICH_TXT  = DIR_BASE.resolve("canciones.txt");
    private static final Path FICH_JSON = DIR_BASE.resolve("canciones.json");

    public static void main(String[] args) {
        List<Cancion> lista = new ArrayList<>();
        int leidas = 0, ignoradas = 0;

        // 1) Leer el TXT (UTF-8) y convertir líneas -> objetos Cancion
        try (var br = Files.newBufferedReader(FICH_TXT)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                leidas++;
                if (linea.isBlank()) continue;

                // Esperamos 5 campos: anio,titulo,artista,mm:ss,esInstrumental
                String[] p = linea.split(",", -1); // -1 preserva posibles campos vacíos
                if (p.length != 5) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (número de campos != 5)");
                    ignoradas++;
                    continue;
                }

                String anioStr = p[0].trim();
                String titulo  = p[1].trim();
                String artista = p[2].trim();
                String duracion = p[3].trim();
                String instrumentalStr = p[4].trim();

                // Validaciones básicas
                Integer anio = parseEntero(anioStr);
                if (anio == null) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (año no numérico)");
                    ignoradas++;
                    continue;
                }

                if (!duracionValida(duracion)) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (duración no válida, formato mm:ss)");
                    ignoradas++;
                    continue;
                }

                Boolean instrumental = parseBoolean(instrumentalStr);
                if (instrumental == null) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (boolean instrumental inválido, usa true/false)");
                    ignoradas++;
                    continue;
                }

                lista.add(new Cancion(anio, titulo, artista, duracion, instrumental));
            }
        } catch (Exception e) {
            System.err.println("Error leyendo " + FICH_TXT + ": " + e.getMessage());
            return;
        }

        // 2) Serializar a JSON con Jackson
        try {
            var mapper = JsonMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT) // salida con formato
                    .build();

            mapper.writeValue(FICH_JSON.toFile(), lista);
        } catch (Exception e) {
            System.err.println("Error escribiendo JSON en " + FICH_JSON + ": " + e.getMessage());
            return;
        }

        // 3) Resumen por consola
        System.out.printf("Leídas: %d  |  Válidas: %d  |  Ignoradas: %d%n",
                leidas, lista.size(), ignoradas);
        System.out.println("JSON generado en: " + FICH_JSON);
    }

    // ---- Utilidades de validación/parsing ----

    private static Integer parseEntero(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Boolean parseBoolean(String s) {
        if (s.equalsIgnoreCase("true")) return true;
        if (s.equalsIgnoreCase("false")) return false;
        return null;
    }

    // Esto es OPCIONAL. No se pide en la actividad
    private static boolean duracionValida(String dur) {
        // formato mm:ss, con 0 <= ss < 60
        String[] t = dur.split(":");
        if (t.length != 2) return false;
        Integer mm = parseEntero(t[0]);
        Integer ss = parseEntero(t[1]);
        if (mm == null || ss == null) return false;
        return ss >= 0 && ss < 60 && mm >= 0;
    }
}
