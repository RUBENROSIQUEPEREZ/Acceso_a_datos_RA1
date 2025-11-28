package es.cifpcarlos3.actividad1_4.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class FamiliaProfesional implements Serializable {
    private String codigo;
    private String nombre;

    @Override
    public String toString() {
        return "Familia profesional: " + nombre + " (" + codigo + ")";
    }
}
