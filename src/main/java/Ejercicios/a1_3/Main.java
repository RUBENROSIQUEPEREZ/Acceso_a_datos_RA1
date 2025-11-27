package Ejercicios.a1_3;

import Ejercicios.a1_3.vo.Ciclo;
import Ejercicios.a1_3.vo.FamiliaProfesional;
import com.ctc.wstx.shaded.msv_core.reader.trex.ng.NGNameState;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {


    static Path txt = Path.of("src", "main", "java", "Ejercicios", "a1_3", "recursos","informacion_ciclos.txt");

    static Path dat = Path.of("src", "main", "java", "Ejercicios", "a1_3", "recursos","familia_profesional.dat");


    //Pasarle un valor como parametro
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Debes ingresas un parametro");
        }

        String codigo = args[0].trim();

        if (familiaProfesional(codigo) == null){
            System.out.println("Familia Profesional no existe: " + codigo);
        }else  {
            System.out.println(familiaProfesional(codigo));
        }

        List<Ciclo> ciclos = cargarCiclo(codigo);



        if (ciclos.isEmpty()){
            System.out.println("Ciclo no existe: " + codigo);
        }else{
            for (Ciclo c : ciclos) {
                System.out.println(c);
            }
        }


    }

    public static FamiliaProfesional familiaProfesional (String parametro){

        //Ejemplo de linea: SAN=Sanidad
        try(var reader = Files.newBufferedReader(dat);)
        {
            String linea;

            while((linea = reader.readLine()) != null){

                linea = linea.trim();

                String [] partes = linea.split("=");

                if (partes.length == 2){

                    String codigo = partes[0].trim();
                    String nombre = partes[1].trim();

                    if (codigo.equalsIgnoreCase(parametro)){

                        return new FamiliaProfesional(codigo, nombre);

                    }
                }
            }
        }catch(Exception e){
            System.out.println("Error al obtener la familia profesional");
        }
        return familiaProfesional(parametro);
    }

    public static List<Ciclo> cargarCiclo (String parametro){
        List<Ciclo> ciclos = new ArrayList<>();
        try(var reader = Files.newBufferedReader(txt);)
        {
            String linea;

            while((linea = reader.readLine()) != null){

                linea = linea.trim();

                //Si no se quitan las comillas el metodo lee 'IFC'
                linea = linea.replace("'","");

                String [] partes = linea.split(",");

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                int horas = Integer.parseInt(partes[2].trim());
                String familia = partes[3].trim();

                //AQUI SERIA UN PROBLEMA
                //Este if iguala 'IFC' = PARAMETRO(EL PARAMETRO INTRODUCIDO VA SIN COMILLAS)
                if (familia.equalsIgnoreCase(parametro)){
                   Ciclo c = new Ciclo(codigo, nombre, horas, familia);
                   ciclos.add(c);
                }
            }

        }catch(Exception e){
            System.out.println("Error al obtener la ciclo");
        }
        return ciclos;
    }
}
