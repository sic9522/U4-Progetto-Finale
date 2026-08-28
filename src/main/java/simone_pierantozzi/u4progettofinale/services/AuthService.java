package simone_pierantozzi.u4progettofinale.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.UnauthorizedException;
import simone_pierantozzi.u4progettofinale.payloads.LoginDTO;
import simone_pierantozzi.u4progettofinale.repositories.UserRepository;
import simone_pierantozzi.u4progettofinale.security.JWTTools;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final JWTTools jwtTools;
	private final PasswordEncoder bcrypt;

	public AuthService(UserRepository userRepository, JWTTools jwtTools, PasswordEncoder bcrypt) {
		this.userRepository = userRepository;
		this.jwtTools = jwtTools;
		this.bcrypt = bcrypt;
	}

	public String checkCredentialsAndGenerateToken(LoginDTO body) {
		User fromDB = userRepository.findByUsername(body.username())
				.orElseThrow(() -> new UnauthorizedException("Credenziali errate"));

		if (!bcrypt.matches(body.password(), fromDB.getPassword()))
			throw new UnauthorizedException("Credenziali errate");

		return jwtTools.generateToken(fromDB);
	}
}
