package Ejercicios.a1_8;


import Ejercicios.a1_8.vo.Ingrediente;
import Ejercicios.a1_8.vo.Receta;
import tools.jackson.databind.json.JsonMapper;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Recetario {

    // Ruta relativa al paquete de la actividad
    private static final Path BASE = Path.of("src","main","java","es","cifpcarlos3","actividad1_8");
    private static final Path JSON = BASE.resolve("receta.json");

    public static void main(String[] args) {
        Receta receta;

        // 1) Leer y deserializar JSON -> Receta
        try (var br = Files.newBufferedReader(JSON, StandardCharsets.UTF_8)) {
            var mapper = JsonMapper.builder().build();
            receta = mapper.readValue(br, Receta.class);
        } catch (Exception e) {
            System.err.println("Error leyendo/deserializando " + JSON + ": " + e.getMessage());
            return;
        }

        // 2) Validación
        try {
            validar(receta);
        } catch (IllegalArgumentException iae) {
            System.err.println("JSON inválido: " + iae.getMessage());
            return;
        }

        // 3) Mostrar resumen
        System.out.printf("Receta: %s (tipo: %s)%n%n", receta.getNombre(), receta.getTipo());
        System.out.println("Ingredientes (" + receta.getIngredientes().size() + "):");
        for (Ingrediente ing : receta.getIngredientes()) {
            System.out.printf("• %s — %s%n", ing.getNombre(), ing.getCantidad());
        }
    }

    // --- Utilidades ---
    private static void validar(Receta r) {
        if (r == null) throw new IllegalArgumentException("Objeto receta nulo");
        if (isBlank(r.getNombre())) throw new IllegalArgumentException("Falta 'nombre'");
        if (isBlank(r.getTipo()))   throw new IllegalArgumentException("Falta 'tipo'");
        if (r.getIngredientes() == null || r.getIngredientes().isEmpty())
            throw new IllegalArgumentException("Falta lista 'ingredientes'");
        for (int i = 0; i < r.getIngredientes().size(); i++) {
            var ing = r.getIngredientes().get(i);
            if (ing == null) throw new IllegalArgumentException("Ingrediente "+(i+1)+" nulo");
            if (isBlank(ing.getNombre()))
                throw new IllegalArgumentException("Ingrediente "+(i+1)+": falta 'nombre'");
            if (isBlank(ing.getCantidad()))
                throw new IllegalArgumentException("Ingrediente "+(i+1)+": falta 'cantidad'");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
