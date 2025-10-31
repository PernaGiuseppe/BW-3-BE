package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.payloads.FatturaFilterPayload;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FatturaSpecification {
    public static Specification<Fattura> byFilters(FatturaFilterPayload filters) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente").get("id"), filters.getClienteId()));
            }

            if (filters.getStatoFatturaId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("statoFattura").get("id"), filters.getStatoFatturaId()));
            }

            if (filters.getDataInizio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("data"), filters.getDataInizio()));
            }

            if (filters.getDataFine() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("data"), filters.getDataFine()));
            }

            if (filters.getAnno() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.function("DATE_PART", Integer.class, criteriaBuilder.literal("year"), root.get("data")), // <-- RIGA CORRETTA
                                filters.getAnno()
                        )
                );
            }

            if (filters.getImportoMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("importo"), filters.getImportoMin()));
            }

            if (filters.getImportoMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("importo"), filters.getImportoMax()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}