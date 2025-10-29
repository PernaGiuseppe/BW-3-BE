package buildweek5.BW_3_BE.services;

import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.UnauthorizedException;
import buildweek5.BW_3_BE.payloads.LoginDTO;
import buildweek5.BW_3_BE.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utentiService;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente found = this.utentiService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}

