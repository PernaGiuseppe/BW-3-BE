package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    boolean existsByCodiceProvinciaAndProgressivoComune(String codiceProvincia, String progressivoComune);
}
