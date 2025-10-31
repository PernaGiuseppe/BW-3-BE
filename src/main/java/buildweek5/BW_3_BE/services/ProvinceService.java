package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Provincia;
import buildweek5.BW_3_BE.repositories.ProvinciaRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.coyote.BadRequestException;
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
public class ProvinceService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Integer upload(MultipartFile file) throws IOException {
        Set<Provincia> province = parseCsv(file);
        Set<Provincia> nuoveProvince = province.stream()
                .filter(p -> provinciaRepository.findBySiglaIgnoreCase(p.getSigla()).isEmpty())
                .collect(Collectors.toSet());

        if (!nuoveProvince.isEmpty()) {
            provinciaRepository.saveAll(nuoveProvince);
        }

        return nuoveProvince.size();
    }

    private Set<Provincia> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){ // questo mi serve per leggere il file che passo come parametro
            reader.mark(1);
            if (reader.read() != 0xFEFF) {
                reader.reset();
            }
            HeaderColumnNameMappingStrategy<ProvinciaRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
           // questo rappresenta la mia strategia di lettura del file
            // che si basa sull'intestazione del file csv. Passo un oggetto i cui attributi
            // richiamano attraverso un binding le varie intestazione del file csv
            strategy.setType(ProvinciaRepresentation.class);
            // csvToBean trasforma un file csv in un bean al builder passo il reader ovvero
            // la stream del file
            CsvToBean<ProvinciaRepresentation> csvToBean =
                    new CsvToBeanBuilder<ProvinciaRepresentation>(reader).
                            withMappingStrategy(strategy).
                            withSeparator(';')
                            .withIgnoreEmptyLine(true).
                            withIgnoreLeadingWhiteSpace(true).
                            build();

            // quando faccio il parse del csvToBean mi torna una lista di ProvinciaRepresentation
           return csvToBean.parse()
                    .stream().map(csv -> Provincia.builder()
                            .sigla(csv.getSigla())
                            .nome(csv.getProvincia())
                            .regione(csv.getRegione())
                            .build()).collect(Collectors.toSet());
        }
    }
}
