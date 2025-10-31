package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Ruolo;
import buildweek5.BW_3_BE.entities.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuoloUtenteRepository extends JpaRepository<RuoloUtente, Long> {
    Optional<RuoloUtente> findByRuoloUtente(Ruolo ruolo);
}

