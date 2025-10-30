package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Ruolo;
import buildweek5.BW_3_BE.entities.RuoloUtente;
import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.UpdateRuoloDTO;
import buildweek5.BW_3_BE.payloads.UtenteDTO;
import buildweek5.BW_3_BE.repositories.RuoloUtenteRepository;
import buildweek5.BW_3_BE.repositories.UtentiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UtenteService {
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;
    @Autowired
    private Cloudinary imageUp;
    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");

    public Utente saveUtenteUser(UtenteDTO payload, Ruolo ruoloassegnato){
        utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("Email " + payload.email() + " già in uso");
        });
        utentiRepository.findByUsername(payload.username()).ifPresent(utente -> {
            throw new BadRequestException("Username " + payload.username() + " già in uso");
        });
        Utente newUt = new Utente();
        newUt.setNome(payload.nome());
        newUt.setCognome(payload.cognome());
        newUt.setEmail(payload.email());
        newUt.setPassword(bcrypt.encode(payload.password()));
        newUt.setUsername(payload.username());
        RuoloUtente ruolo = ruoloUtenteRepository.findByRuoloUtente(Ruolo.USER)
                .orElseThrow(() -> new NotFoundException("Ruolo USER non trovato"));

        newUt.setRuoli(new ArrayList<>());
        newUt.getRuoli().add(ruolo);
        newUt.setAvatar("https://ui-avatars.com/api/?name=" + payload.nome() + "+" + payload.cognome());
        Utente savedUtente = utentiRepository.save(newUt);
        log.info("Utente " + savedUtente.getUsername() + " salvato correttamente");
        return savedUtente;
    }
    public Utente findById(Long id){
        return utentiRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public Utente findByIdAndUpdate(Long id, UtenteDTO payload, Ruolo ruoloassegnato) {
        Utente found = this.findById(id);
        if (!found.getEmail().equals(payload.email())) {
            utentiRepository.findByEmail(payload.email()).ifPresent(dipendente -> {
                throw new BadRequestException("L' e-mail " + payload.email() + " è già in uso");
            });
        }
        if (!found.getUsername().equals(payload.username())) {
            utentiRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
                throw new BadRequestException("L' usernname" + payload.username() + " è già in uso");
            });
        }
        RuoloUtente ruolo = ruoloUtenteRepository.findByRuoloUtente(Ruolo.USER).
                orElseThrow(() -> new BadRequestException("Ruolo Non Trovato"));
        found.setRuoli(new ArrayList<>());
        found.setEmail(payload.email());
        found.getRuoli().add(ruolo);
        found.setPassword(bcrypt.encode(payload.password()));
        Utente updatedUtente = utentiRepository.save(found);
        log.info("Utente " + found.getId() + " aggiornato correttamente");
        return updatedUtente;
    }

    public void deleteUtente(Long id){
        Utente found = this.findById(id);
        utentiRepository.delete(found);
    }
    public Utente findByEmail(String email){
        return utentiRepository.findByEmail(email).orElseThrow(()->new NotFoundException("L'utente con email non è stato trovato"));
    }
    public Utente udpdateRuolo(Long id, Ruolo nuovoRuolo){
        if (nuovoRuolo != Ruolo.ADMIN && nuovoRuolo != Ruolo.USER) {
            throw new BadRequestException("Il ruolo specificato non è valido. I ruoli consentiti sono ADMIN e USER.");
        }
        Utente found = this.findById(id);
        if (found.getRuoli() == null) {
            found.setRuoli(new ArrayList<>());
        }
        RuoloUtente ruoloEntity = ruoloUtenteRepository.findByRuoloUtente(nuovoRuolo)
                .orElseThrow(() -> new NotFoundException("Ruolo " + nuovoRuolo + " non trovato"));
        if (!found.getRuoli().contains(ruoloEntity)) {
            found.getRuoli().add(ruoloEntity);
        }
        Utente updated = utentiRepository.save(found);
        log.info("Ruolo di " + found.getUsername() + " aggiornato");
        return updated;
    }
    public Utente findByIdAndUpImg(Long id, MultipartFile file){
        Utente found = this.findById(id);
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
            found.setAvatar(urlImg);
            Utente newImgDip = utentiRepository.save(found);
            log.info("Immagine del dipendente " + found.getId() + " aggiornata correttamente");
            return newImgDip;

        } catch (IOException e) {
            throw new BadRequestException("Errore nel caricamento dell'immagine");
        }

    }

}
