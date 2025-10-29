package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirizziRepository extends JpaRepository<Indirizzo, Long> {
}
