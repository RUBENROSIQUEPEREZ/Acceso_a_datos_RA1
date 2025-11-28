package Tarea.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("Apellidos")
    String apellido;
    @JsonProperty("Nombre")
    String nombre;
    @JsonProperty("Ciudad")
    String ciudad;
    @JsonProperty("Edad")
    int edad;
    @JsonProperty("Fecha_de_registro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaRegistro;
}
