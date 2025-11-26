package es.cifpcarlos3.vo;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor



public class Curso implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    private String nombre;

    @JacksonXmlElementWrapper(localName = "Lista_de_alumnos")
    @JacksonXmlProperty(localName = "Alumno")
    private List <Alumno> ListaAlumnos = new ArrayList<>();
}
