package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, Long> {
    List<Fattura> findByCliente(Cliente cliente);
    Optional<Fattura> findByNumero(String numero);

    Page<Fattura> findByClienteId(Long id, Pageable pageable);

    List<Fattura> findByStatoFattura(StatoFattura stato);

    Page<Fattura> findByStatoFatturaId(Long id, Pageable pageable);

    Page<Fattura> findByDataBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<Fattura> findByImportoBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    List<Fattura> findByAnno(@Param("anno") int anno);

    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    Page<Fattura> findByAnno(@Param("anno") int anno, Pageable pageable);
}
