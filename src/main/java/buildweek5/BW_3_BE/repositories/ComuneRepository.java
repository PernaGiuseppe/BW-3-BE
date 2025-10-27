package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    List<Comune> findByProvincia_Sigla(String sigla);

    List<Comune> findByDenominazioneContainingIgnoreCase(String denominazione);
}


