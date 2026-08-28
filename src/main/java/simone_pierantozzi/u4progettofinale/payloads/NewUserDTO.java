package simone_pierantozzi.u4progettofinale.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
		@NotBlank(message = "Lo username è obbligatorio")
		String username,

		@NotBlank(message = "Il nome completo è obbligatorio")
		String nomeCompleto,

		@NotBlank(message = "L'email è obbligatoria")
		@Email(message = "L'indirizzo inserito non è un'email valida")
		String email,

		@NotBlank(message = "La password è obbligatoria")
		@Size(min = 4, message = "La password deve essere di almeno 4 caratteri")
		String password
) {
}
