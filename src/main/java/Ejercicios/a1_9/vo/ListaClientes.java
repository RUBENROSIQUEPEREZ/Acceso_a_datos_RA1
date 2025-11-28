package Ejercicios.a1_9.vo;

import lombok.*;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonRootName("ListaClientes")
public class ListaClientes {
    @JacksonXmlElementWrapper(useWrapping = false)      // <cliente> repetido, sin <clientes> interno adicional
    @JacksonXmlProperty(localName = "cliente")
    private List<Cliente> clientes;
}