package simone_pierantozzi.u4progettofinale.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simone_pierantozzi.u4progettofinale.entities.Role;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.NotFoundException;
import simone_pierantozzi.u4progettofinale.exceptions.ValidationException;
import simone_pierantozzi.u4progettofinale.payloads.NewRoleDTO;
import simone_pierantozzi.u4progettofinale.payloads.NewUserDTO;
import simone_pierantozzi.u4progettofinale.payloads.UserRespDTO;
import simone_pierantozzi.u4progettofinale.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder bcrypt;

	public UserService(UserRepository userRepository, PasswordEncoder bcrypt) {
		this.userRepository = userRepository;
		this.bcrypt = bcrypt;
	}

	public User create(NewUserDTO payload) {
		if (userRepository.findByUsername(payload.username()).isPresent())
			throw new ValidationException("Lo username " + payload.username() + " è già in uso");

		User newUser = new User(payload.username(), payload.nomeCompleto(), payload.email(), bcrypt.encode(payload.password()));
		return userRepository.save(newUser);
	}

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
	}

	public UserRespDTO updateRole(Long id, NewRoleDTO payload) {
		User user = this.findById(id);
		user.setRole(Role.valueOf(payload.role()));
		User saved = userRepository.save(user);
		return new UserRespDTO(saved.getId(), saved.getUsername(), saved.getNomeCompleto(), saved.getEmail(), saved.getRole());
	}
}
