package Ejercicios.a1_7;



import Ejercicios.a1_7.vo.CatalogoCoches;
import Ejercicios.a1_7.vo.Coche;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.databind.SerializationFeature;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GestorCoches {

    // Rutas relativas a la carpeta del paquete de la actividad
    private static final Path BASE = Path.of("src","main","java","es","cifpcarlos3","actividad1_7");
    private static final Path TXT  = BASE.resolve("coches.txt");
    private static final Path XML  = BASE.resolve("coches.xml");

    public static void main(String[] args) {
        List<Coche> lista = new ArrayList<>();
        int leidas = 0, ignoradas = 0;

        // 1) Leer TXT y convertir líneas -> Coche
        try (var br = Files.newBufferedReader(TXT, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                leidas++;
                if (linea.isBlank()) continue;

                // Esperamos: marca,modelo,color,anio
                String[] p = linea.split(",", -1);
                if (p.length != 4) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (nº de campos != 4)");
                    ignoradas++;
                    continue;
                }

                String marca  = p[0].trim();
                String modelo = p[1].trim();
                String color  = p[2].trim();
                Integer anio  = parseEntero(p[3].trim());
                if (anio == null) {
                    System.out.println("Aviso: línea " + leidas + " ignorada (año no numérico)");
                    ignoradas++;
                    continue;
                }

                lista.add(new Coche(marca, modelo, color, anio));
            }
        } catch (Exception e) {
            System.err.println("Error leyendo " + TXT + ": " + e.getMessage());
            return;
        }

        // 2) Serializar a XML con Jackson 3
        try {
            var xmlMapper = XmlMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT) // serializa con formato
                    .build();

            Files.createDirectories(BASE); // por si falta la carpeta
            var catalogo = new CatalogoCoches(lista);
            xmlMapper.writeValue(XML.toFile(), catalogo);
        } catch (Exception e) {
            System.err.println("Error escribiendo XML en " + XML + ": " + e.getMessage());
            return;
        }

        // 3) Resumen
        System.out.printf("Leídas: %d  |  Válidas: %d  |  Ignoradas: %d%n", leidas, lista.size(), ignoradas);
        System.out.println("XML generado en: " + XML);
    }

    private static Integer parseEntero(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return null; }
    }
}
