package buildweek5.BW_3_BE.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FatturaFilterDTO {
    private Long clienteId;
    private Long statoFatturaId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Integer anno;
    private BigDecimal importoMin;
    private BigDecimal importoMax;
}
