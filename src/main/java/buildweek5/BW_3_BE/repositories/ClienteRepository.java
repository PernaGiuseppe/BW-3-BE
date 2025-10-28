package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPartitaIva(String partitaIva);

    Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String nome, Pageable pageable);

    List<Cliente> findByFatturatoAnnualeBetween(BigDecimal min, BigDecimal max);

    List<Cliente> findByDataInserimentoBetween(LocalDate start, LocalDate end);

    List<Cliente> findByDataUltimoContattoBetween(LocalDate start, LocalDate end);
}
