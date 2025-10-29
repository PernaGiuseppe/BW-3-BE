package buildweek5.BW_3_BE.runners;

import buildweek5.BW_3_BE.entities.StatoFattura;
import buildweek5.BW_3_BE.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class StatoFatturaInizializer implements CommandLineRunner {
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    @Override
    public void run(String... args) throws Exception {
        if(statoFatturaRepository.count() == 0){
            statoFatturaRepository.save(new StatoFattura(null, "EMESSA"));
            statoFatturaRepository.save(new StatoFattura(null, "PAGATA"));
            statoFatturaRepository.save(new StatoFattura(null, "SCADUTA"));
        }
    }
}
