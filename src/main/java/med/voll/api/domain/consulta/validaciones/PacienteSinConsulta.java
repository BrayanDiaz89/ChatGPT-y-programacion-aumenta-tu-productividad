package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos)  {
        if (datos == null) {
            throw new ValidationException("Los datos de la consulta no pueden ser nulos");
        }
        // Validar que la fecha no sea nula
        if (datos.fecha() == null) {
            throw new ValidationException("La fecha de la consulta no puede ser nula");
        }
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario= datos.fecha().withHour(18);

        var pacienteConConsulta=repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidationException("el paciente ya tiene una consulta para ese dia");
        }

    }
}
