package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.payloads.ClienteFilterPayload;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {

    public static Specification<Cliente> byFilters(ClienteFilterPayload filters) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.getContieneNome() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("ragioneSociale")),
                                "%" + filters.getContieneNome().toLowerCase() + "%"
                        )
                );
            }
            if (filters.getFatturatoMin() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), filters.getFatturatoMin())
                );
            }
            if (filters.getFatturatoMax() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("fatturatoAnnuale"), filters.getFatturatoMax())
                );
            }
            if (filters.getDataInserimentoInizio() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataInserimento"), filters.getDataInserimentoInizio())
                );
            }
            if (filters.getDataInserimentoFine() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("dataInserimento"), filters.getDataInserimentoFine())
                );
            }

            if (filters.getDataUltimoContattoInizio() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataUltimoContatto"), filters.getDataUltimoContattoInizio())
                );
            }

            if (filters.getDataUltimoContattoFine() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("dataUltimoContatto"), filters.getDataUltimoContattoFine())
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}