package intento1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Curso implements Serializable {
    private String nombre;

    @JacksonXmlElementWrapper(localName = "ListaDeAlumnos")
    @JacksonXmlProperty(localName = "Alumno")
    private List<Alumno> alumnos;
}
