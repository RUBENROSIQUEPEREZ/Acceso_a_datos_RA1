package Ejercicios.a1_9;



import Ejercicios.a1_9.vo.Cliente;
import Ejercicios.a1_9.vo.ListaClientes;
import Ejercicios.a1_9.vo.Sucursal;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.DeserializationFeature;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestorClientes {

    // Rutas relativas a la carpeta del paquete de la actividad
    private static final Path BASE = Path.of("src", "main", "java", "es", "cifpcarlos3", "actividad1_9");
    private static final Path XML  = BASE.resolve("clientes.xml");

    public static void main(String[] args) {
        // 1) Preparar XmlMapper (Jackson 3) y leer el XML
        ListaClientes datos;
        try {
            var xmlMapper = XmlMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();

            try (var br = Files.newBufferedReader(XML, StandardCharsets.UTF_8)) {
                datos = xmlMapper.readValue(br, ListaClientes.class);
            }
        } catch (Exception e) {
            System.err.println("Error leyendo/deserializando " + XML + ": " + e.getMessage());
            return;
        }

        // 2) Validar estructura
        try {
            validar(datos);
        } catch (IllegalArgumentException iae) {
            System.err.println("XML inválido: " + iae.getMessage());
            return;
        }

        // 3) Mostrar resumen por consola
        imprimirResumen(datos);
    }

    // --------- Validación ----------
    private static void validar(ListaClientes raiz) {
        if (raiz == null || raiz.getClientes() == null || raiz.getClientes().isEmpty()) {
            throw new IllegalArgumentException("No hay clientes.");
        }

        for (int i = 0; i < raiz.getClientes().size(); i++) {
            Cliente c = raiz.getClientes().get(i);
            if (c == null) throw new IllegalArgumentException("Cliente " + (i + 1) + " nulo.");
            if (c.getId() <= 0) throw new IllegalArgumentException("Cliente " + (i + 1) + ": id inválido.");
            if (isBlank(c.getNombre())) throw new IllegalArgumentException("Cliente " + (i + 1) + ": falta 'nombre'.");

            if (c.getSucursales() == null || c.getSucursales().isEmpty()) {
                throw new IllegalArgumentException("Cliente " + (i + 1) + ": no hay sucursales.");
            }

            for (int j = 0; j < c.getSucursales().size(); j++) {
                Sucursal s = c.getSucursales().get(j);
                if (s == null) throw new IllegalArgumentException("Sucursal " + (j + 1) + " nula (cliente " + (i + 1) + ").");
                if (isBlank(s.getCalle()))
                    throw new IllegalArgumentException("Sucursal " + (j + 1) + ": falta 'calle' (cliente " + (i + 1) + ").");
                if (isBlank(s.getCiudad()))
                    throw new IllegalArgumentException("Sucursal " + (j + 1) + ": falta 'ciudad' (cliente " + (i + 1) + ").");
                // provincia y cp son opcionales
            }
        }
    }

    // --------- Salida legible ----------
    private static void imprimirResumen(ListaClientes datos) {
        for (Cliente c : datos.getClientes()) {
            System.out.printf("Cliente: %s (id: %d)%n%n", c.getNombre(), c.getId());
            System.out.println("Sucursales (" + c.getSucursales().size() + "):");
            for (Sucursal s : c.getSucursales()) {
                String prov = isBlank(s.getProvincia()) ? "" : " [" + s.getProvincia() + "]";
                String cp   = isBlank(s.getCp())        ? "" : " — CP " + s.getCp();
                System.out.printf("• %s, %s%s%s%n", s.getCalle(), s.getCiudad(), prov, cp);
            }
            System.out.println();
        }
    }

    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
}
