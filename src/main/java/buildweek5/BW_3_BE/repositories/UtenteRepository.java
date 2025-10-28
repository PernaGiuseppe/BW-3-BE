package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByEmail(String email);
}

