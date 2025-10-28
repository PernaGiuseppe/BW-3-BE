package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Indirizzo;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.ClienteFilterDTO;
import buildweek5.BW_3_BE.repositories.ClienteRepository;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private IndirizzoRepository indirizzoRepo;

    @Autowired
    private ComuneRepository comuneRepo;

    public Cliente createCliente(ClienteDTO dto) {
        // Validazione partita IVA e email univoche
        if (clienteRepo.findByPartitaIva(dto.getPartitaIva()).isPresent()) {
            throw new BadRequestException("Partita IVA già esistente");
        }

        if (clienteRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email già esistente");
        }

        // Crea e salva gli indirizzi
        Indirizzo indirizzoLegale = creaIndirizzo(dto.getIndirizzoLegale());
        Indirizzo indirizzoOperativo = creaIndirizzo(dto.getIndirizzoOperativo());

        // Crea il cliente
        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(dto.getRagioneSociale());
        cliente.setPartitaIva(dto.getPartitaIva());
        cliente.setEmail(dto.getEmail());
        cliente.setDataInserimento(LocalDate.now());
        cliente.setFatturatoAnnuale(dto.getFatturatoAnnuale());
        cliente.setPec(dto.getPec());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmailContatto(dto.getEmailContatto());
        cliente.setNomeContatto(dto.getNomeContatto());
        cliente.setCognomeContatto(dto.getCognomeContatto());
        cliente.setTelefonoContatto(dto.getTelefonoContatto());
        cliente.setLogoAziendale(dto.getLogoAziendale());
        cliente.setTipoCliente(dto.getTipoCliente());
        cliente.setIndirizzoLegale(indirizzoLegale);
        cliente.setIndirizzoOperativo(indirizzoOperativo);

        return clienteRepo.save(cliente);
    }

    private Indirizzo creaIndirizzo(buildweek5.BW_3_BE.payloads.IndirizzoDTO dto) {
        Comune comune = comuneRepo.findById(dto.getComuneId())
                .orElseThrow(() -> new NotFoundException("Comune non trovato"));

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(dto.getVia());
        indirizzo.setCivico(dto.getCivico());
        indirizzo.setLocalita(dto.getLocalita());
        indirizzo.setCap(dto.getCap());
        indirizzo.setComune(comune);

        return indirizzoRepo.save(indirizzo);
    }

    public Page<Cliente> findAllClienti(Pageable pageable) {
        return clienteRepo.findAll(pageable);
    }

    public Cliente findById(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con id " + id + " non trovato"));
    }

    public Page<Cliente> filterClienti(ClienteFilterDTO filters, Pageable pageable) {
        // Implementazione base - per ora ritorna tutti
        // Puoi implementare filtri più complessi usando Specification
        return clienteRepo.findAll(pageable);
    }

    public Cliente updateCliente(Long id, ClienteDTO dto) {
        Cliente cliente = findById(id);

        // Verifica unicità partita IVA (se modificata)
        if (!cliente.getPartitaIva().equals(dto.getPartitaIva())) {
            if (clienteRepo.findByPartitaIva(dto.getPartitaIva()).isPresent()) {
                throw new BadRequestException("Partita IVA già esistente");
            }
            cliente.setPartitaIva(dto.getPartitaIva());
        }

        // Verifica unicità email (se modificata)
        if (!cliente.getEmail().equals(dto.getEmail())) {
            if (clienteRepo.findByEmail(dto.getEmail()).isPresent()) {
                throw new BadRequestException("Email già esistente");
            }
            cliente.setEmail(dto.getEmail());
        }

        // Aggiorna campi
        cliente.setRagioneSociale(dto.getRagioneSociale());
        cliente.setDataUltimoContatto(LocalDate.now());
        cliente.setFatturatoAnnuale(dto.getFatturatoAnnuale());
        cliente.setPec(dto.getPec());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmailContatto(dto.getEmailContatto());
        cliente.setNomeContatto(dto.getNomeContatto());
        cliente.setCognomeContatto(dto.getCognomeContatto());
        cliente.setTelefonoContatto(dto.getTelefonoContatto());
        cliente.setLogoAziendale(dto.getLogoAziendale());
        cliente.setTipoCliente(dto.getTipoCliente());

        return clienteRepo.save(cliente);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = findById(id);
        clienteRepo.delete(cliente);
    }
}
