package Tarea.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno implements Serializable {
    String id;
    String apellido;
    String nombre;
    String ciudad;
    int edad;
    LocalDate fechaRegistro;
}
