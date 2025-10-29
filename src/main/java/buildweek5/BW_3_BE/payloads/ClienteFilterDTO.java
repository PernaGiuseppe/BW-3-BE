package buildweek5.BW_3_BE.payloads;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteFilterDTO(
        BigDecimal fatturatoMin,
        BigDecimal fatturatoMax,
        LocalDate dataInserimentoInizio,
        LocalDate dataInserimentoFine,
        LocalDate dataUltimoContattoInizio,
        LocalDate dataUltimoContattoFine,
        String parteNome,
        String provinciaSedeLegale
) {
}
