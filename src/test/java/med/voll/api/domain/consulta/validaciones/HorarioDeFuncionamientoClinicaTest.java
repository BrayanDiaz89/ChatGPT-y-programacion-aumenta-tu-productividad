package med.voll.api.domain.consulta.validaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class HorarioDeFuncionamientoClinicaTest {

    private final HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();

    @Test
    void debePermitirConsultaEnHorarioValido() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 10, 0)); // Lunes, 10:00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }

    @Test
    void debeLanzarExcepcionSiEsDomingo() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 7, 10, 0)); // Domingo, 10:00

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> validador.validar(datosMock));
        assertEquals("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas", exception.getMessage());
    }

    @Test
    void debeLanzarExcepcionSiEsAntesDeLaApertura() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 6, 0)); // Lunes, 06:00

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> validador.validar(datosMock));
        assertEquals("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas", exception.getMessage());
    }

    @Test
    void debeLanzarExcepcionSiEsDespuesDelCierre() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 20, 0)); // Lunes, 20:00

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> validador.validar(datosMock));
        assertEquals("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas", exception.getMessage());
    }

    @Test
    void debePermitirConsultaExactamenteApertura() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 7, 0)); // Lunes, 07:00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }

    @Test
    void debePermitirConsultaExactamenteCierre() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 19, 0)); // Lunes, 19:00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }
    @Test
    void debeLanzarExcepcionSiFechaEsNula() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(null);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datosMock));
    }
    @Test
    void debeLanzarExcepcionSiDatosAgendarConsultaEsNulo() {
        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(null));
    }
    @Test
    void debePermitirConsultaExactamenteAlAbrir() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 7, 0)); // Lunes, 07:00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }
    @Test
    void debePermitirConsultaExactamenteAntesDeCerrar() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 18, 59)); // Lunes, 18:59

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }
    @Test
    void debePermitirConsultaEnFechaFuturaValida() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2074, 1, 8, 10, 0)); // Lunes, 10:00 en el futuro

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(datosMock));
    }
    @Test
    void debeLanzarExcepcionSiUnMinutoAntesDeAbrir() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 6, 59)); // Lunes, 06:59

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> validador.validar(datosMock));
        assertEquals("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas", exception.getMessage());
    }
    @Test
    void debeLanzarExcepcionSiUnMinutoDespuesDeCerrar() {
        // Arrange
        DatosAgendarConsulta datosMock = mock(DatosAgendarConsulta.class);
        when(datosMock.fecha()).thenReturn(LocalDateTime.of(2024, 1, 8, 19, 1)); // Lunes, 19:01

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> validador.validar(datosMock));
        assertEquals("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas", exception.getMessage());
    }
}
