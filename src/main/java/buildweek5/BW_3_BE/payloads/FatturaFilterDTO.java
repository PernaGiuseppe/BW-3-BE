package buildweek5.BW_3_BE.payloads;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FatturaFilterDTO(
        Long clienteId,
        Long statoFatturaId,
        LocalDate dataInizio,
        LocalDate dataFine,
        Integer anno,
        BigDecimal importoMin,
        BigDecimal importoMax
) {
}
