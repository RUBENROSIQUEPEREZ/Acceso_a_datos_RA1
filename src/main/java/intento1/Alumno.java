package intento1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno implements Serializable {
    private String apellido;
    private String nombre;
    private String ciudad;
    private int edad;
    private LocalDate fechaDeRegistro;
}
