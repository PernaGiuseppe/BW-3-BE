package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Provincia;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.ProvinciaRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComuniService {
        @Autowired
        private ProvinciaRepository provinciaRepository;
        @Autowired
        private ComuneRepository comuneRepository;

        public Integer upload(MultipartFile file) throws IOException {
            Set<Comune> comuni = parseCsv(file);
            comuneRepository.saveAll(comuni);
            return comuni.size();
        }

        private Set<Comune> parseCsv(MultipartFile file) throws IOException {
            try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){ // questo mi serve per leggere il file che passo come parametro
                reader.mark(1);
                if (reader.read() != 0xFEFF) {
                    reader.reset();
                }
                HeaderColumnNameMappingStrategy<ComuneRepresentation> strategy =
                        new HeaderColumnNameMappingStrategy<>();
                // questo rappresenta la mia strategia di lettura del file
                // che si basa sull'intestazione del file csv. Passo un oggetto i cui attributi
                // richiamano attraverso un binding le varie intestazione del file csv
                strategy.setType(ComuneRepresentation.class);
                // csvToBean trasforma un file csv in un bean al builder passo il reader ovvero
                // la stream del file
                CsvToBean<ComuneRepresentation> csvToBean =
                        new CsvToBeanBuilder<ComuneRepresentation>(reader).
                                withMappingStrategy(strategy).
                                withSeparator(';')
                                .withIgnoreEmptyLine(true).
                                withIgnoreLeadingWhiteSpace(true).
                                build();

                // quando faccio il parse del csvToBean mi torna una lista di ProvinciaRepresentation
                return csvToBean.parse()
                        .stream()
                        .map(csv -> {
                            Comune comune = Comune.builder()
                                    .codiceProvincia(csv.getCodiceProvincia())
                                    .progressivoComune(csv.getProgressivoComune())
                                    .denominazione(csv.getNomeComune())
                                    .build();

                            provinciaRepository.findByNomeIgnoreCase(csv.getProvincia())
                                    .ifPresent(comune::setProvincia);

                            return comune;
                        })
                        .collect(Collectors.toSet());
        }
    }

}
