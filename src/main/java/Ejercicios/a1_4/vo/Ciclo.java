package es.cifpcarlos3.actividad1_4.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Ciclo implements Serializable {
    private String codigo;
    private String nombre;
    private int horas;
    private String codigoFamilia;
    private String codigoGrado;

    @Override
    public String toString() {
        return "'" + codigo + "', '" + nombre + "', " + horas + ", '" + codigoFamilia + "', '" + codigoGrado + "'";
    }
}
