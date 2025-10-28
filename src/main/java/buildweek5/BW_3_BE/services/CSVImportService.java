package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.controllers.Mapping;
import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Provincia;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.ProvinciaRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVImportService {

    @Autowired
    private ProvinciaRepository provinciaRepo;

    @Autowired
    private ComuneRepository comuneRepo;

    public void importProvince(String filePath) throws Exception {
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';').build())
                .build()) {

            reader.readNext();
            String[] line;

            List<Provincia> province = new ArrayList<>();

            while ((line = reader.readNext()) != null) {
                Provincia p = new Provincia();
                p.setSigla(line[0].trim());
                p.setNome(line[1].trim());
                p.setRegione(line[2].trim());
                province.add(p);
            }

            provinciaRepo.saveAll(province);
        }
    }

    public void importComuni(String filePath) throws Exception {
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';').build())
                .build()) {

            reader.readNext();
            String[] line;

            List<Comune> comuni = new ArrayList<>();
            int batchSize = 1000;

            while ((line = reader.readNext()) != null) {
                Comune c = new Comune();
                c.setCodiceProvincia(line[0].trim());
                c.setProgressivoComune(line[1].trim());
                c.setDenominazione(line[2].trim());

                String sigla = Mapping.getSiglaFromCodice(line[0].trim());
                Provincia provincia = provinciaRepo.findById(sigla)
                        .orElseThrow(() -> new RuntimeException(
                                "Provincia non trovata: " + sigla));
                c.setProvincia(provincia);

                comuni.add(c);

                if (comuni.size() >= batchSize) {
                    comuneRepo.saveAll(comuni);
                    comuni.clear();
                }
            }

            if (!comuni.isEmpty()) {
                comuneRepo.saveAll(comuni);
            }
        }
    }
}

