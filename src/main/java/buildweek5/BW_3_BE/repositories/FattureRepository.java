package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, Long>, JpaSpecificationExecutor<Fattura> {
    Optional<Fattura> findByNumero(String numero);


}
