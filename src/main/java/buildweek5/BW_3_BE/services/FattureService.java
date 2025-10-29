package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.entities.StatoFattura;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.FatturaDT0;
import buildweek5.BW_3_BE.payloads.FatturaFilterPayload;
import buildweek5.BW_3_BE.repositories.ClientiRepository;
import buildweek5.BW_3_BE.repositories.FattureRepository;
import buildweek5.BW_3_BE.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FattureService {

    @Autowired
    private FattureRepository fatturaRepo;

    @Autowired
    private ClientiRepository clienteRepo;

    @Autowired
    private StatoFatturaRepository statoRepo;


    public Fattura createFattura(FatturaDT0 dto) {
        Cliente cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        StatoFattura stato = statoRepo.findById(dto.statoFatturaId())
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato"));
        if (fatturaRepo.findByNumero(dto.numero()).isPresent()) {
            throw new BadRequestException("Numero fattura già esistente");
        }

        Fattura fattura = new Fattura();
        fattura.setNumero(dto.numero());
        fattura.setData(dto.data());
        fattura.setImporto(dto.importo());
        fattura.setCliente(cliente);
        fattura.setStatoFattura(stato);

        return fatturaRepo.save(fattura);
    }

    public Page<Fattura> findAll(int npagine, int nsize, String sortBy) {
        if (nsize >= 10) nsize = 10;
        Pageable pageable = PageRequest.of(npagine, nsize, Sort.by(sortBy));
        return fatturaRepo.findAll(pageable);
    }

    public Fattura findById(Long id) {
        return fatturaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata"));
    }

    public Page<Fattura> filterFatture(FatturaFilterPayload filters, int npagine, int nsize, String sortBy) {
        if (nsize >= 10) nsize = 10;
        Pageable pageable = PageRequest.of(npagine, nsize, Sort.by(sortBy));
        if(filters.getClienteId() != null){
            return fatturaRepo.findByClienteId(filters.getClienteId(), pageable);
        } else if (filters.getStatoFatturaId() != null) {
            return fatturaRepo.findByStatoFatturaId(filters.getStatoFatturaId(), pageable);
        } else if (filters.getAnno() != null) {
            return fatturaRepo.findByAnno(filters.getAnno(), pageable);
        } else if (filters.getImportoMin() != null && filters.getImportoMax() != null) {
            return fatturaRepo.findByImportoBetween(filters.getImportoMin(),filters.getImportoMax(),pageable);
        } else if (filters.getDataInizio() != null && filters.getDataFine() != null) {
            return fatturaRepo.findByDataBetween(filters.getDataInizio(), filters.getDataFine(), pageable);
        }

        return fatturaRepo.findAll(pageable);
    }


    public Fattura updateFattura(Long id, FatturaDT0 dto) {
        Fattura fattura = findById(id);

        Cliente cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        StatoFattura stato = statoRepo.findById(dto.statoFatturaId())
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato"));
        if (fatturaRepo.findByNumero(dto.numero()).isPresent()) {
            throw new BadRequestException("Numero fattura già esistente");
        }


        fattura.setNumero(dto.numero());
        fattura.setData(dto.data());
        fattura.setImporto(dto.importo());
        fattura.setCliente(cliente);
        fattura.setStatoFattura(stato);

        return fatturaRepo.save(fattura);
    }


    public void deleteFattura(Long id) {
        Fattura fattura = findById(id);
        fatturaRepo.delete(fattura);
    }
}

