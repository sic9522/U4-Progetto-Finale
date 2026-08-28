package simone_pierantozzi.u4progettofinale.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.ValidationException;
import simone_pierantozzi.u4progettofinale.payloads.LoginDTO;
import simone_pierantozzi.u4progettofinale.payloads.LoginRespDTO;
import simone_pierantozzi.u4progettofinale.payloads.NewUserDTO;
import simone_pierantozzi.u4progettofinale.payloads.NewUserRespDTO;
import simone_pierantozzi.u4progettofinale.services.AuthService;
import simone_pierantozzi.u4progettofinale.services.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final UserService userService;

	public AuthController(AuthService authService, UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public LoginRespDTO login(@RequestBody @Validated LoginDTO payload, BindingResult validationResult) {
		if (validationResult.hasErrors()) {
			String errorsList = validationResult.getFieldErrors()
					.stream()
					.map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.joining(". "));
			throw new ValidationException(errorsList);
		}

		String accessToken = this.authService.checkCredentialsAndGenerateToken(payload);
		return new LoginRespDTO(accessToken);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public NewUserRespDTO register(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
		if (validationResult.hasErrors()) {
			String errorsList = validationResult.getFieldErrors()
					.stream()
					.map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.joining(". "));
			throw new ValidationException(errorsList);
		}

		User newUser = this.userService.create(payload);
		return new NewUserRespDTO(newUser.getId());
	}
}
