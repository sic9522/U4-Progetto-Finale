package simone_pierantozzi.u4progettofinale.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(User user) {
		return Jwts.builder()
				.subject(String.valueOf(user.getId()))
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
				.compact();
	}

	public void verifyToken(String token) {
		try {
			Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
		} catch (Exception ex) {
			throw new UnauthorizedException("Token non valido, effettua di nuovo il login");
		}
	}

	public Long extractIdFromToken(String token) {
		String subject = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
		return Long.parseLong(subject);
	}
}
