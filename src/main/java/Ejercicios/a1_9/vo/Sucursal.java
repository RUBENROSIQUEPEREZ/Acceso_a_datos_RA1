package Ejercicios.a1_9.vo;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class Sucursal {
    private String calle;
    private String ciudad;
    private String provincia; // opcional
    private String cp;        // opcional
}