package xyz.mmonteiroc.eshop.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.User;
import xyz.mmonteiroc.eshop.exceptions.TokenNotValidException;
import xyz.mmonteiroc.eshop.exceptions.TokenOverdatedException;
import xyz.mmonteiroc.eshop.repository.UserRepository;

import java.util.Date;
import java.util.Objects;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 21/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class TokenManager {

    @Autowired
    private Environment environment;
    @Autowired
    private UserRepository userRepository;

    private String createToken(String email, Date timeout) {
        String secret = environment.getProperty("JWT_SECRET");
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(timeout)
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(secret))
                .compact();
    }

    public String createAccessToken(User user) {
        long milis = Long.parseLong(Objects.requireNonNull(environment.getProperty("ACCESS_TOKEN_EXPIRE")));
        return this.createToken(user.getEmail(), new Date(System.currentTimeMillis() + milis));
    }

    public String createRefreshToken(User user) {
        long milis = Long.parseLong(Objects.requireNonNull(environment.getProperty("REFRESH_TOKEN_EXPIRE")));
        return this.createToken(user.getEmail(), new Date(System.currentTimeMillis() + milis));
    }

    public boolean validateToken(String token) throws TokenOverdatedException, TokenNotValidException {
        try {
            Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenOverdatedException();
        } catch (Exception e) {
            throw new TokenNotValidException();
        }
    }

    public String getEmailbyToken(String token) throws TokenNotValidException {
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("email");

        } catch (Exception e) {
            throw new TokenNotValidException();
        }
    }

    public String getRol(String token) throws TokenNotValidException {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("rol");

        } catch (Exception e) {
            throw new TokenNotValidException();
        }
    }

    public Claims getClaims(String token) throws TokenNotValidException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new TokenNotValidException();
        }
        return claims;
    }

    public User getUsuarioFromToken(String token) throws TokenNotValidException {
        Claims claims = getClaims(token);
        Long idTokenUsuario = Long.parseLong(claims.get("idusuario").toString());
        return userRepository.findByIdUser(idTokenUsuario);

    }
}
