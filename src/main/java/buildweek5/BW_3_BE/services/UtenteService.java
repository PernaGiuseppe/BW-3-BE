package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.RuoloUtente;
import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotFoundException;
import buildweek5.BW_3_BE.payloads.UtenteRegistrazioneDTO;
import buildweek5.BW_3_BE.repositories.RuoloUtenteRepository;
import buildweek5.BW_3_BE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private RuoloUtenteRepository ruoloRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Utente register(UtenteRegistrazioneDTO dto) {
        if (utenteRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("Username già esistente");
        }

        if (utenteRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email già esistente");
        }

        Utente utente = new Utente();
        utente.setUsername(dto.getUsername());
        utente.setEmail(dto.getEmail());
        utente.setPassword(passwordEncoder.encode(dto.getPassword()));
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setAvatar(dto.getAvatar());

        // Assegna ruolo USER di default
        RuoloUtente ruolo = ruoloRepo.findByRuoloUtente("USER")
                .orElseThrow(() -> new NotFoundException("Ruolo USER non trovato"));

        utente.setRuoli(new ArrayList<>());
        utente.getRuoli().add(ruolo);

        return utenteRepo.save(utente);
    }

    public Utente findById(Long id) {
        return utenteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
    }

    public Utente findByUsername(String username) {
        return utenteRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente con username " + username + " non trovato"));
    }
}

