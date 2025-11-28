package Tarea.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso implements Serializable {
    private String nombre;

    @JacksonXmlElementWrapper(localName = "Lista_de_alumnos")
    @JacksonXmlProperty(localName = "Alumno")
    private List<Alumno> alumnos;
}
