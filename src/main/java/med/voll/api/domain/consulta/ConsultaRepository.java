package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.dto.DatosRelatoriosConsultaMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    @Query("""
        SELECT new med.voll.api.domain.consulta.dto.DatosRelatoriosConsultaMensual(m.nombre, COUNT(c))
        FROM Consulta c
        JOIN c.medico m
        WHERE FUNCTION('YEAR', c.fecha) = :year
          AND FUNCTION('MONTH', c.fecha) = :month
        GROUP BY m.nombre
        ORDER BY COUNT(c) DESC
    """)
    List<DatosRelatoriosConsultaMensual> buscarConsultasPorMes(int year, int month);

}

