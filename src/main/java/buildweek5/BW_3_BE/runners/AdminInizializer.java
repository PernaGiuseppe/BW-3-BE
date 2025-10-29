package buildweek5.BW_3_BE.runners;

import buildweek5.BW_3_BE.entities.Ruolo;
import buildweek5.BW_3_BE.entities.RuoloUtente;
import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.repositories.RuoloUtenteRepository;
import buildweek5.BW_3_BE.repositories.UtentiRepository;
import buildweek5.BW_3_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class AdminInizializer implements CommandLineRunner {
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.nome}")
    private String adminNome;
    @Value("${admin.cognome}")
    private String adminCognome;

    @Override
    public void run(String... args) throws Exception {
        if (utentiRepository.findByEmail(adminEmail).isEmpty()) {
            Utente admin = new Utente();
            admin.setNome(adminNome);
            admin.setCognome(adminCognome);
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(bcrypt.encode(adminPassword));
            admin.setAvatar("https://ui-avatars.com/api/?name=" + admin.getNome() + "+" + admin.getCognome());
            RuoloUtente ruoloAdmin = ruoloUtenteRepository.findByRuoloUtente(Ruolo.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Ruolo ADMIN non trovato"));
            admin.setRuoli(List.of(ruoloAdmin));
            utentiRepository.save(admin);
        }
    }
}
