package Ejercicios.a1_3.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciclo {
    private String cod;
    private String nombre;
    private int horas;
    private String familia;
}
