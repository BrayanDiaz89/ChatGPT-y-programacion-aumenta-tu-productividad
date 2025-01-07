package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PacienteSinConsultaTest {

    @Mock
    private ConsultaRepository repository;

    @InjectMocks
    private PacienteSinConsulta validador;

    private DatosAgendarConsulta datos;

    @BeforeEach
    void setup() {
        // Crea un objeto válido de DatosAgendarConsulta
        datos = new DatosAgendarConsulta(
                1L,                           // idPaciente
                2L,                           // idMedico (puede ser nulo si no lo necesitas)
                LocalDateTime.of(2025, 1, 7, 9, 0), // fecha válida en el futuro
                Especialidad.CARDIOLOGIA         // Especialidad (ajusta según tu dominio)
        );
    }

    @Test
    void debeLanzarExcepcionSiPacienteTieneConsulta() {
        // Configura el mock para simular que el paciente ya tiene una consulta
        when(repository.existsByPacienteIdAndFechaBetween(
                Mockito.eq(datos.idPaciente()),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        )).thenReturn(true);

        // Verifica que se lance la excepción esperada
        assertThrows(ValidationException.class, () -> validador.validar(datos));
    }

    @Test
    void noDebeLanzarExcepcionSiPacienteNoTieneConsulta() {
        // Configura el mock para simular que el paciente NO tiene una consulta
        when(repository.existsByPacienteIdAndFechaBetween(
                Mockito.eq(datos.idPaciente()),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        )).thenReturn(false);

        // Verifica que no se lance ninguna excepción
        validador.validar(datos);
    }
    @Test
    void debeLanzarExcepcionSiPacienteTieneVariasConsultas() {
        when(repository.existsByPacienteIdAndFechaBetween(
                Mockito.eq(datos.idPaciente()),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        )).thenReturn(true); // Simula múltiples consultas

        assertThrows(ValidationException.class, () -> validador.validar(datos));
    }
    @Test
    void debeLanzarExcepcionSiDatosSonNulos() {
        assertThrows(ValidationException.class, () -> validador.validar(null));
    }
    @Test
    void debeLanzarExcepcionSiFechaEsNula() {
        DatosAgendarConsulta datosConFechaNula = new DatosAgendarConsulta(
                1L,
                2L,
                null,
                Especialidad.CARDIOLOGIA
        );
        assertThrows(ValidationException.class, () -> validador.validar(datosConFechaNula));
    }
    @Test
    void noDebeLanzarExcepcionSiIdMedicoEsNulo() {
        DatosAgendarConsulta datosSinIdMedico = new DatosAgendarConsulta(
                1L,
                null,
                LocalDateTime.of(2025, 1, 7, 9, 0),
                Especialidad.CARDIOLOGIA
        );
        validador.validar(datosSinIdMedico);
    }

}

