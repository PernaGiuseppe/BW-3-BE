package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Comune;
import buildweek5.BW_3_BE.entities.Indirizzo;
import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.ClienteFilterPayload;
import buildweek5.BW_3_BE.payloads.IndirizzoDTO;
import buildweek5.BW_3_BE.repositories.ClientiRepository;
import buildweek5.BW_3_BE.repositories.ComuneRepository;
import buildweek5.BW_3_BE.repositories.IndirizziRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClientiRepository clienteRepo;

    @Autowired
    private IndirizziRepository indirizzoRepo;

    @Autowired
    private ComuneRepository comuneRepo;
    @Autowired
    private Cloudinary imageUp;
    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");


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

    public Page<Cliente> findAllClientiFiltered(ClienteFilterPayload filters, int npagine, int nsize, String sortBy) {
        if (nsize >= 30) nsize = 30;
        Pageable pageable = PageRequest.of(npagine, nsize, Sort.by(sortBy));
        Specification<Cliente> spec = ClienteSpecification.byFilters(filters);
        return clienteRepo.findAll(spec,pageable);
    }

    public Cliente findById(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con id " + id + " non trovato"));
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
    public Page<Cliente> getClientiOrdinatiPerProvincia(int page, int size, String direction) {
        Pageable pageable = PageRequest.of(page, size);
        if (direction.equalsIgnoreCase("desc")) {
            return clienteRepo.findAllOrderByProvinciaNomeDesc(pageable);
        } else {
            return clienteRepo.findAllOrderByProvinciaNomeAsc(pageable);
        }
    }
    public Cliente findByIdAndUpImg(Long id, MultipartFile file){
        Cliente found = this.findById(id);
        // gestisco le eccezioni su formati e grandezza o se il file è vuoto
        if (file.isEmpty()) throw new BadRequestException("File Vuoto");
        if (file.getSize() > MAX_SIZE) throw new BadRequestException("File troppo grande");
        if (!ALLOWED_TYPES.contains(file.getContentType()))
            throw new BadRequestException("Formato file non supportato");

        // gestisco l'upload
        try {
            // upload chiede il file più una map vuota o con dettagli aggettivi
            Map result = imageUp.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // recupero l'url dal risultato
            String urlImg = (String) result.get("url");
            // setto l'url all'autore
            found.setLogoAziendale(urlImg);
            Cliente newImgCliente = clienteRepo.save(found);
            log.info("Immagine del dipendente " + found.getId() + " aggiornata correttamente");
            return newImgCliente;

        } catch (IOException e) {
            throw new BadRequestException("Errore nel caricamento dell'immagine");
        }

    }
}

