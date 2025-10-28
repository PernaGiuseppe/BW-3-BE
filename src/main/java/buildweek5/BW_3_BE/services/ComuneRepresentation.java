package buildweek5.BW_3_BE.services;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComuneRepresentation {

    @CsvBindByName(column = "Codice Provincia (Storico)(1)")
    private String codiceProvincia;

    @CsvBindByName(column = "Progressivo del Comune (2)")
    private String progressivoComune;

    @CsvBindByName(column = "Denominazione in italiano")
    private String nomeComune;

    @CsvBindByName(column = "Provincia")
    private String provincia;
}
