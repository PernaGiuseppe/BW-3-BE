package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.StatoFattura;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.StatoFatturaDTO;
import buildweek5.BW_3_BE.repositories.StatoFatturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatoFatturaService {
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public StatoFattura createStato(StatoFatturaDTO payload){
        if(statoFatturaRepository.findByStatoFattura(payload.statoFattura()).isPresent()){
            throw new BadRequestException("Stato Già Esistente");
        }
        StatoFattura newS = new StatoFattura();
        newS.setStatoFattura(payload.statoFattura());
        StatoFattura newStato = statoFatturaRepository.save(newS);
        log.info("Nuovo stato fattura aggiunto");
        return newStato;
    }

    public StatoFattura findById(Long id){
        return statoFatturaRepository.findById(id).orElseThrow(()-> new NotFoundException("Stato Non trovato"));
    }

    public StatoFattura findByIdAndUpdate(Long id, StatoFatturaDTO payload){
        StatoFattura found = this.findById(id);
        if(statoFatturaRepository.findByStatoFattura(payload.statoFattura()).isPresent()){
            throw new BadRequestException("Stato Già Esistente");
        }
        found.setStatoFattura(payload.statoFattura());
        StatoFattura newStato = statoFatturaRepository.save(found);
        log.info("Stato Aggiornato");
        return newStato;
    }
    public void delete(Long id){
        StatoFattura found = this.findById(id);
        statoFatturaRepository.delete(found);
    }
}
