package simone_pierantozzi.u4progettofinale.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewRoleDTO(
		@NotBlank(message = "Il ruolo è obbligatorio")
		String role
) {
}
