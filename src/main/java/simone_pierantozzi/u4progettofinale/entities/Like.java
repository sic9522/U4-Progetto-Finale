package simone_pierantozzi.u4progettofinale.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@Setter(AccessLevel.NONE)
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	@Setter(AccessLevel.NONE)
	private Post post;

	public Like(User user, Post post) {
		this.user = user;
		this.post = post;
	}
}
