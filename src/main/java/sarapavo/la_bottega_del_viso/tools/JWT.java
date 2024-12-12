package sarapavo.la_bottega_del_viso.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sarapavo.la_bottega_del_viso.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWT {
    @Value("${jwt.secret}")
    private String secret;


    public String createToken(String email) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(email) //  NON METTERE DATI SENSIBILI QUA DENTRO!!
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    // Verifica il token JWT
    public void verifyToken(String accessToken) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(accessToken);
            // .parse() lancerà diversi tipi di eccezioni a seconda che il token sia stato o manipolato, o sia scaduto o sia malformato
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi con il token! Per favore effettua di nuovo il login!");
        }
    }

    // Estrae l'ID dell'utente dal token JWT
    public String getIdFromToken(String accessToken) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(accessToken)
                .getPayload().getSubject();
    }


}
