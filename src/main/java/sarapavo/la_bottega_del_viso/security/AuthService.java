package sarapavo.la_bottega_del_viso.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.UnauthorizedException;
import sarapavo.la_bottega_del_viso.tools.JWT;
import sarapavo.la_bottega_del_viso.user.LoginDTO;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserService;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UserService userService;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User found = this.userService.findByEmail(body.email());
        if (found == null) {
            logger.warn("Tentativo di accesso fallito per email: {}", body.email());
            throw new UnauthorizedException("Credenziali errate! Utente non trovato.");
        }

        if (bcrypt.matches(body.password(), found.getPassword())) {
            logger.info("Autenticazione riuscita per email: {}", body.email());
            String accessToken = jwt.createToken(found);
            return accessToken;
        } else {
            logger.warn("Autenticazione fallita per email: {} - password errata", body.email());
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
