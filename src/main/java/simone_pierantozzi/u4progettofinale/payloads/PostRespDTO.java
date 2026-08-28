package simone_pierantozzi.u4progettofinale.payloads;

import java.time.LocalDateTime;

public record PostRespDTO(
		Long id,
		String text,
		LocalDateTime publicationDate,
		UserRespDTO author
) {
}
