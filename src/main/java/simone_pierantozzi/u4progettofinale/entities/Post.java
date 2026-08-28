package simone_pierantozzi.u4progettofinale.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;

	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private LocalDateTime publicationDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@Setter(AccessLevel.NONE)
	private User user;

	public Post(String text, LocalDateTime publicationDate, User user) {
		this.text = text;
		this.publicationDate = publicationDate;
		this.user = user;
	}
}
