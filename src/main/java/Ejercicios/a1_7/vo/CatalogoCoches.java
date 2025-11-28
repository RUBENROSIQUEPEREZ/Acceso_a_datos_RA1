package Ejercicios.a1_7.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("catalogo")
public class CatalogoCoches {

    // Genera elementos <coche> repetidos sin un envoltorio <coches>
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "coche")
    private List<Coche> coches;
}
