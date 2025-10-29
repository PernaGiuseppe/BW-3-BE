package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientiRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByRagioneSociale(String ragioneSociale);

    List<Cliente> findByRagioneSocialeContainingIgnoreCase(String ragioneSociale);

    List<Cliente> findByFatturatoAnnualeBetween(BigDecimal min, BigDecimal max);

    List<Cliente> findByDataInserimentoBetween(LocalDate start, LocalDate end);

    List<Cliente> findByDataUltimoContattoBetween(LocalDate start, LocalDate end);
}
