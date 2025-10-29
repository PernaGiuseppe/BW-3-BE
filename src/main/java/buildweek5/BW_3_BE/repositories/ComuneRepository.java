package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    boolean existsByCodiceProvinciaAndProgressivoComune(String codiceProvincia, String progressivoComune);
}
