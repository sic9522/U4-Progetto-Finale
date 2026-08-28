package simone_pierantozzi.u4progettofinale.payloads;

import simone_pierantozzi.u4progettofinale.entities.Role;

public record UserRespDTO(
		Long id,
		String username,
		String nomeCompleto,
		String email,
		Role role
) {
}
