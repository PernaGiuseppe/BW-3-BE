package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura,Long> {
    Optional<StatoFattura> findByStatoFattura(String stato);
}
