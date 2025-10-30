package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.entities.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientiRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByRagioneSociale(String ragioneSociale);

    Page<Cliente> findByRagioneSocialeContainingIgnoreCase(String ragioneSociale, Pageable pageable);

    Page<Cliente> findByFatturatoAnnualeBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Cliente> findByDataInserimentoBetween(LocalDate start, LocalDate end, Pageable pageable);
    Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(BigDecimal fatturato, Pageable pageable);
    Page<Cliente> findByFatturatoAnnualeLessThanEqual(BigDecimal fatturato, Pageable pageable);
    Page<Cliente> findByDataInserimento(LocalDate start, Pageable pageable);
    Page<Cliente> findByDataUltimoContatto(LocalDate end, Pageable pageable);
    Page<Cliente> findByDataUltimoContattoBetween(LocalDate start, LocalDate end, Pageable pageable);
    List<Cliente> findByIndirizzoLegale_Comune_Provincia(Provincia provincia);
    @Query("SELECT c FROM Cliente c " +
            "JOIN c.indirizzoLegale il " +
            "JOIN il.comune co " +
            "JOIN co.provincia p " +
            "ORDER BY p.nome ASC")
    Page<Cliente> findAllOrderByProvinciaNomeAsc(Pageable pageable);

    @Query("SELECT c FROM Cliente c " +
            "JOIN c.indirizzoLegale il " +
            "JOIN il.comune co " +
            "JOIN co.provincia p " +
            "ORDER BY p.nome DESC")
    Page<Cliente> findAllOrderByProvinciaNomeDesc(Pageable pageable);
}
