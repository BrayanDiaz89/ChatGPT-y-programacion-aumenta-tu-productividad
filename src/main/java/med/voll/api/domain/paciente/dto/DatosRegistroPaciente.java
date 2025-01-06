package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;


public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,

        @NotBlank
        //@Size(min = 0, max = 15) código del curso
        @Pattern(regexp = "^3\\d{2}\\d{7}$", message = "El número de celular debe tener el formato colombiano: iniciar con 3, seguido de 9 dígitos.")
        String telefono,
        //@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") código de la clase
        //Código nuevo. Expresión regular para documento identidad colombiano, por chatgpt
        @Pattern(regexp = "^\\d{6,10}(-\\d{1})?$", message = "El documento debe tener de 6 a 10 dígitos, opcionalmente seguido de un guion y un dígito.")
        @NotBlank
        String documento,

        @NotNull @Valid DatosDireccion direccion) {
}
