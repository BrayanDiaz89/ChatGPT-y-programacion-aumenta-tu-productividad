package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {
        // Validar que el objeto datos no sea nulo
        if (datos == null) {
            throw new ValidationException("Los datos de la consulta no pueden ser nulos");
        }
        // Validar que la fecha no sea nula
        if (datos.fecha() == null) {
            throw new ValidationException("La fecha de la consulta no puede ser nula");
        }
        // Obtener los datos necesarios para la validación
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        int horaDelDia = datos.fecha().getHour();
        int minuto = datos.fecha().getMinute();

        if(domingo || horaDelDia < 7 || (horaDelDia == 19 && minuto > 0) || horaDelDia > 19){
            throw  new ValidationException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
