package buildweek5.BW_3_BE.repositories;

import buildweek5.BW_3_BE.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findBySiglaIgnoreCase(String sigla);
    Optional<Provincia> findByNomeIgnoreCase(String nome);
}
