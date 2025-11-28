package Ejercicios.a1_9.vo;

import lombok.*;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class Cliente {
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private long id;

    private String nombre;

    @JacksonXmlElementWrapper(useWrapping = false)      // varios <sucursal> sin contenedor
    @JacksonXmlProperty(localName = "sucursal")
    private List<Sucursal> sucursales;
}