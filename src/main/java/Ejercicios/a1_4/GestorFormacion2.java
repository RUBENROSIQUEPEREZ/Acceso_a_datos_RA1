package es.cifpcarlos3.actividad1_4;

import es.cifpcarlos3.actividad1_4.vo.*;
import java.io.*;
import java.util.*;

public class GestorFormacion2 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java GestorFormacion2 <codigo_familia> <codigo_grado>");
            return;
        }

        String codigoFamilia = args[0].trim().toUpperCase();
        String codigoGrado = args[1].trim().toUpperCase();

        FamiliaProfesional familia = cargarFamilia(codigoFamilia);
        Grado grado = cargarGrado(codigoGrado);

        if (familia == null) {
            System.out.println("!! No se ha encontrado la familia profesional con código: " + codigoFamilia);
            return;
        }

        if (grado == null) {
            System.out.println("!! No se ha encontrado el grado con código: " + codigoGrado);
            return;
        }

        System.out.println(familia);
        System.out.println("Grado seleccionado: " + grado.getNombre());

        List<Ciclo> ciclos = cargarCiclos(codigoFamilia, codigoGrado);

        if (ciclos.isEmpty()) {
            System.out.println("!! No hay ciclos asociados a esa familia y grado.");
        } else {
            System.out.println("\nCiclos encontrados:\n");
            for (Ciclo c : ciclos) {
                System.out.printf("El ciclo %s incluido en la familia de %s es un %s.%n",
                        c.getNombre(), familia.getNombre(), grado.getNombre());
            }

            // Serializar la lista de ciclos
            serializarLista(ciclos);
            System.out.println("\nLista serializada correctamente en lista_ciclos.ser");
        }
    }

    private static FamiliaProfesional cargarFamilia(String codigo) {
        File file = new File("src/main/java/es/cifpcarlos3/actividad1_4/familia_profesional.dat");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("=");
                if (partes.length == 2) {
                    String cod = partes[0].trim();
                    String nombre = partes[1].trim();
                    if (cod.equalsIgnoreCase(codigo)) {
                        return new FamiliaProfesional(cod, nombre);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("!! Error leyendo familia_profesional.dat: " + e.getMessage());
        }
        return null;
    }

    private static Grado cargarGrado(String codigo) {
        File file = new File("src/main/java/es/cifpcarlos3/actividad1_4/grados.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("codigo_grado")) continue;

                String[] partes = linea.split(";");
                if (partes.length >= 2) {
                    String cod = partes[0].trim();
                    String nombre = partes[1].trim();
                    String categoria = (partes.length > 2) ? partes[2].trim() : "";

                    if (cod.equalsIgnoreCase(codigo)) {
                        return new Grado(cod, nombre, categoria);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("!! Error leyendo grados.csv: " + e.getMessage());
        }
        return null;
    }

    private static List<Ciclo> cargarCiclos(String codigoFamilia, String codigoGrado) {
        List<Ciclo> lista = new ArrayList<>();
        File file = new File("src/main/java/es/cifpcarlos3/actividad1_4/informacion_ciclos.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String limpia = linea.replace("'", "").trim();
                String[] partes = limpia.split(",");

                if (partes.length == 5) {
                    String cod = partes[0].trim();
                    String nombre = partes[1].trim();
                    int horas = Integer.parseInt(partes[2].trim());
                    String codFamilia = partes[3].trim();
                    String codGrado = partes[4].trim();

                    if (codFamilia.equalsIgnoreCase(codigoFamilia) && codGrado.equalsIgnoreCase(codigoGrado)) {
                        lista.add(new Ciclo(cod, nombre, horas, codFamilia, codGrado));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("!! Error leyendo informacion_ciclos.txt: " + e.getMessage());
        }

        return lista;
    }

    private static void serializarLista(List<Ciclo> ciclos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("src/main/java/es/cifpcarlos3/actividad1_4/lista_ciclos.ser"))) {
            oos.writeObject(ciclos);
        } catch (IOException e) {
            System.err.println("!! Error serializando la lista de ciclos: " + e.getMessage());
        }
    }
}