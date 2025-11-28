package Ejercicios.a1_6.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cancion {
    private int anio;           // año de publicación
    private String titulo;      // título de la canción
    private String artista;     // artista/grupo
    private String duracion;    // "mm:ss"
    @JsonProperty("es_Español")
    private boolean esEpanol;
}