package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Provincia;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CsvImportService implements CommandLineRunner {

    private static final Map<String, String> CODICE_TO_SIGLA = Map.ofEntries(
            Map.entry("001", "TO"), Map.entry("002", "VC"), Map.entry("003", "NO"),
            Map.entry("004", "CN"), Map.entry("005", "AT"), Map.entry("006", "AL"),
            Map.entry("007", "AO"), Map.entry("008", "IM"), Map.entry("009", "SV"),
            Map.entry("010", "GE"), Map.entry("011", "SP"), Map.entry("012", "VA"),
            Map.entry("013", "CO"), Map.entry("014", "SO"), Map.entry("015", "MI"),
            Map.entry("016", "BG"), Map.entry("017", "BS"), Map.entry("018", "PV"),
            Map.entry("019", "CR"), Map.entry("020", "MN"), Map.entry("021", "BZ"),
            Map.entry("022", "TN"), Map.entry("023", "VR"), Map.entry("024", "VI"),
            Map.entry("025", "BL"), Map.entry("026", "TV"), Map.entry("027", "VE"),
            Map.entry("028", "PD"), Map.entry("029", "RO"), Map.entry("030", "UD"),
            Map.entry("031", "GO"), Map.entry("032", "TS"), Map.entry("033", "PC"),
            Map.entry("034", "PR"), Map.entry("035", "RE"), Map.entry("036", "MO"),
            Map.entry("037", "BO"), Map.entry("038", "FE"), Map.entry("039", "RA"),
            Map.entry("040", "FC"), Map.entry("041", "PU"), Map.entry("042", "AN"),
            Map.entry("043", "MC"), Map.entry("044", "AP"), Map.entry("045", "MS"),
            Map.entry("046", "LU"), Map.entry("047", "PT"), Map.entry("048", "FI"),
            Map.entry("049", "LI"), Map.entry("050", "PI"), Map.entry("051", "AR"),
            Map.entry("052", "SI"), Map.entry("053", "GR"), Map.entry("054", "PG"),
            Map.entry("055", "TR"), Map.entry("056", "VT"), Map.entry("057", "RI"),
            Map.entry("058", "Roma"), Map.entry("059", "LT"), Map.entry("060", "FR"),
            Map.entry("061", "CE"), Map.entry("062", "BN"), Map.entry("063", "NA"),
            Map.entry("064", "AV"), Map.entry("065", "SA"), Map.entry("066", "AQ"),
            Map.entry("067", "TE"), Map.entry("068", "PE"), Map.entry("069", "CH"),
            Map.entry("070", "CB"), Map.entry("071", "FG"), Map.entry("072", "BA"),
            Map.entry("073", "TA"), Map.entry("074", "BR"), Map.entry("075", "LE"),
            Map.entry("076", "PZ"), Map.entry("077", "MT"), Map.entry("078", "CS"),
            Map.entry("079", "CZ"), Map.entry("080", "RC"), Map.entry("081", "TP"),
            Map.entry("082", "PA"), Map.entry("083", "ME"), Map.entry("084", "AG"),
            Map.entry("085", "CL"), Map.entry("086", "EN"), Map.entry("087", "CT"),
            Map.entry("088", "RG"), Map.entry("089", "SR"), Map.entry("090", "SS"),
            Map.entry("091", "NU"), Map.entry("092", "CA"), Map.entry("093", "PN"),
            Map.entry("094", "IS"), Map.entry("095", "OR"), Map.entry("096", "BI"),
            Map.entry("097", "LC"), Map.entry("098", "LO"), Map.entry("099", "RN"),
            Map.entry("100", "PO"), Map.entry("101", "KR"), Map.entry("102", "VV"),
            Map.entry("103", "VB"), Map.entry("108", "MB"), Map.entry("109", "FM"),
            Map.entry("110", "BT"), Map.entry("111", "VS")
    );
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private ComuneRepository comuneRepository;

    @Override
    public void run(String... args) throws Exception {
        // Controlla se i dati sono già stati importati
        if (provinciaRepository.count() > 0) {
            log.info("✅ Dati già presenti nel database. Import saltato.");
            return;
        }

        log.info("🚀 Avvio importazione CSV...");

        importProvince();
        importComuni();

        log.info("✅ Importazione completata con successo!");
    }

    private void importProvince() throws Exception {
        log.info("📥 Importazione province...");

        var resource = getClass().getClassLoader()
                .getResourceAsStream("province-italiane.csv");

        if (resource == null) {
            throw new RuntimeException("File province-italiane.csv non trovato!");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource, StandardCharsets.UTF_8))) {

            reader.readLine(); // Skip header
            String line;
            List<Provincia> province = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                Provincia provincia = new Provincia();
                provincia.setSigla(parts[0].trim());
                provincia.setNome(parts[1].trim());
                provincia.setRegione(parts[2].trim());

                province.add(provincia);
            }

            provinciaRepository.saveAll(province);
            log.info("✅ {} province importate", province.size());
        }
    }

    private void importComuni() throws Exception {
        log.info("📥 Importazione comuni...");

        var resource = getClass().getClassLoader()
                .getResourceAsStream("comuni-italiani.csv");

        if (resource == null) {
            throw new RuntimeException("File comuni-italiani.csv non trovato!");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource, StandardCharsets.UTF_8))) {

            reader.readLine(); // Skip header
            String line;
            List<Comune> comuni = new ArrayList<>();
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                Comune comune = new Comune();
                comune.setCodiceProvincia(parts[0].trim());
                comune.setProgressivoComune(parts[1].trim());
                comune.setDenominazione(parts[2].trim());

                String sigla = CODICE_TO_SIGLA.get(parts[0].trim());

                if (sigla == null) {
                    log.warn("⚠️ Sigla non trovata per codice: {}", parts[0].trim());
                    continue;
                }

                Provincia provincia = provinciaRepository.findById(sigla)
                        .orElseThrow(() -> new RuntimeException(
                                "Provincia non trovata per sigla: " + sigla));
                comune.setProvincia(provincia);

                comuni.add(comune);

                if (++count % 500 == 0) {
                    comuneRepository.saveAll(comuni);
                    comuni.clear();
                    log.info("📊 {} comuni importati...", count);
                }
            }

            if (!comuni.isEmpty()) {
                comuneRepository.saveAll(comuni);
            }

            log.info("✅ {} comuni importati totali", count);
        }
    }
}
