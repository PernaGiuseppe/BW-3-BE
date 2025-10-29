package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Indirizzo;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.ClienteFilterPayload;
import buildweek5.BW_3_BE.payloads.IndirizzoDTO;
import buildweek5.BW_3_BE.repositories.ClientiRepository;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.IndirizziRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClienteService {

    @Autowired
    private ClientiRepository clienteRepo;

    @Autowired
    private IndirizziRepository indirizzoRepo;

    @Autowired
    private ComuneRepository comuneRepo;


    public Cliente createCliente(ClienteDTO dto) {
        if (clienteRepo.findByPartitaIva(dto.partitaIva()).isPresent()) {
            throw new BadRequestException("Partita IVA già esistente");
        }

        if (clienteRepo.findByEmail(dto.email()).isPresent()) {
            throw new BadRequestException("Email già esistente");
        }
        if (clienteRepo.findByPec(dto.pec()).isPresent()) {
            throw new BadRequestException("Pec già esistente");
        }
        if (clienteRepo.findByRagioneSociale(dto.ragioneSociale()).isPresent()) {
            throw new BadRequestException("Ragione sociale già esistente");
        }

        Indirizzo indirizzoLegale = creaIndirizzo(dto.indirizzoLegale());
        Indirizzo indirizzoOperativo = creaIndirizzo(dto.indirizzoOperativo());


        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(dto.ragioneSociale());
        cliente.setPartitaIva(dto.partitaIva());
        cliente.setEmail(dto.email());
        cliente.setDataInserimento(LocalDate.now());
        cliente.setFatturatoAnnuale(dto.fatturatoAnnuale());
        cliente.setPec(dto.pec());
        cliente.setTelefono(dto.telefono());
        cliente.setEmailContatto(dto.emailContatto());
        cliente.setNomeContatto(dto.nomeContatto());
        cliente.setCognomeContatto(dto.cognomeContatto());
        cliente.setTelefonoContatto(dto.telefonoContatto());
        cliente.setTipoCliente(dto.tipoCliente());
        cliente.setIndirizzoLegale(indirizzoLegale);
        cliente.setIndirizzoOperativo(indirizzoOperativo);

        return clienteRepo.save(cliente);
    }

    private Indirizzo creaIndirizzo(IndirizzoDTO dto) {
        Comune comune = comuneRepo.findById(dto.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune non trovato"));

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(dto.via());
        indirizzo.setCivico(dto.civico());
        indirizzo.setLocalita(dto.localita());
        indirizzo.setCap(dto.cap());
        indirizzo.setComune(comune);

        return indirizzoRepo.save(indirizzo);
    }

    public Page<Cliente> findAllClienti(int npagine, int nsize, String sortBy) {
        if (nsize >= 30) nsize = 30;
        Pageable pageable = PageRequest.of(npagine, nsize, Sort.by(sortBy));
        return clienteRepo.findAll(pageable);
    }

    public Cliente findById(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con id " + id + " non trovato"));
    }

    public Page<Cliente> filterClienti(ClienteFilterPayload filters, int npagine, int nsize, String sortBy) {
        if (nsize >= 30) nsize = 30;
        Pageable pageable = PageRequest.of(npagine, nsize, Sort.by(sortBy));
        return clienteRepo.findAll(pageable);
    }


    public Cliente updateCliente(Long id, ClienteDTO dto) {
        Cliente cliente = findById(id);

        if (!cliente.getPartitaIva().equals(dto.partitaIva())) {
            if (clienteRepo.findByPartitaIva(dto.partitaIva()).isPresent()) {
                throw new BadRequestException("Partita IVA già esistente");
            }
            cliente.setPartitaIva(dto.partitaIva());
        }


        if (!cliente.getEmail().equals(dto.email())) {
            if (clienteRepo.findByEmail(dto.email()).isPresent()) {
                throw new BadRequestException("Email già esistente");
            }
            cliente.setEmail(dto.email());
        }

        if (!cliente.getPec().equals(dto.pec())) {
            if (clienteRepo.findByPec(dto.pec()).isPresent()) {
                throw new BadRequestException("Pec già esistente");
            }
            cliente.setPec(dto.pec());
        }
        if (!cliente.getRagioneSociale().equals(dto.ragioneSociale())) {
            if (clienteRepo.findByRagioneSociale(dto.ragioneSociale()).isPresent()) {
                throw new BadRequestException("Ragione sociale già esistente");
            }
            cliente.setRagioneSociale(dto.ragioneSociale());
        }


        cliente.setDataUltimoContatto(LocalDate.now());
        cliente.setFatturatoAnnuale(dto.fatturatoAnnuale());
        cliente.setTelefono(dto.telefono());
        cliente.setEmailContatto(dto.emailContatto());
        cliente.setNomeContatto(dto.nomeContatto());
        cliente.setCognomeContatto(dto.cognomeContatto());
        cliente.setTelefonoContatto(dto.telefonoContatto());
        cliente.setTipoCliente(dto.tipoCliente());

        return clienteRepo.save(cliente);
    }


    public void deleteCliente(Long id) {
        Cliente cliente = findById(id);
        clienteRepo.delete(cliente);
    }
}

