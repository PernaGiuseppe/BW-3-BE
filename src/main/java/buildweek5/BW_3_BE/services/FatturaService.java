package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.entities.StatoFattura;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.FatturaDTO;
import buildweek5.BW_3_BE.payloads.FatturaFilterDTO;
import buildweek5.BW_3_BE.repositories.ClienteRepository;
import buildweek5.BW_3_BE.repositories.FatturaRepository;
import buildweek5.BW_3_BE.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private StatoFatturaRepository statoRepo;

    public Fattura createFattura(FatturaDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));
        StatoFattura stato = statoRepo.findById(dto.getStatoFatturaId())
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato"));

        Fattura fattura = new Fattura();
        fattura.setNumero(dto.getNumero());
        fattura.setData(dto.getData());
        fattura.setImporto(dto.getImporto());
        fattura.setCliente(cliente);
        fattura.setStatoFattura(stato);

        return fatturaRepo.save(fattura);
    }

    public Page<Fattura> findAll(Pageable pageable) {
        return fatturaRepo.findAll(pageable);
    }

    public Fattura findById(Long id) {
        return fatturaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata"));
    }

    public Page<Fattura> filterFatture(FatturaFilterDTO filters, Pageable pageable) {
        // Implementazione base - per ora ritorna tutte
        // Puoi implementare filtri più complessi usando Specification
        return fatturaRepo.findAll(pageable);
    }

    public Fattura updateFattura(Long id, FatturaDTO dto) {
        Fattura fattura = findById(id);
        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));
        StatoFattura stato = statoRepo.findById(dto.getStatoFatturaId())
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato"));

        fattura.setNumero(dto.getNumero());
        fattura.setData(dto.getData());
        fattura.setImporto(dto.getImporto());
        fattura.setCliente(cliente);
        fattura.setStatoFattura(stato);

        return fatturaRepo.save(fattura);
    }

    public void deleteFattura(Long id) {
        Fattura fattura = findById(id);
        fatturaRepo.delete(fattura);
    }
}
