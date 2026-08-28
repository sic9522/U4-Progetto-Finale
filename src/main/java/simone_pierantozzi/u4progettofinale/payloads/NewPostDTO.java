package simone_pierantozzi.u4progettofinale.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewPostDTO(
		@NotBlank(message = "Il testo del post è obbligatorio")
		String text
) {
}
