package simone_pierantozzi.u4progettofinale.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simone_pierantozzi.u4progettofinale.exceptions.ValidationException;
import simone_pierantozzi.u4progettofinale.payloads.NewRoleDTO;
import simone_pierantozzi.u4progettofinale.payloads.UserRespDTO;
import simone_pierantozzi.u4progettofinale.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PatchMapping("/{userId}/role")
	@PreAuthorize("hasAuthority('MODERATOR')")
	public UserRespDTO changeRole(@PathVariable Long userId, @RequestBody @Validated NewRoleDTO payload, BindingResult validationResult) {
		if (validationResult.hasErrors()) {
			StringBuilder errorsBuilder = new StringBuilder();
			for (int i = 0; i < validationResult.getFieldErrors().size(); i++) {
				errorsBuilder.append(validationResult.getFieldErrors().get(i).getDefaultMessage());
				if (i < validationResult.getFieldErrors().size() - 1) {
					errorsBuilder.append(". ");
				}
			}
			throw new ValidationException(errorsBuilder.toString());
		}

		return this.userService.updateRole(userId, payload);
	}
}
