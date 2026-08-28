package simone_pierantozzi.u4progettofinale.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import simone_pierantozzi.u4progettofinale.entities.Post;
import simone_pierantozzi.u4progettofinale.entities.Role;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.NotFoundException;
import simone_pierantozzi.u4progettofinale.payloads.NewPostDTO;
import simone_pierantozzi.u4progettofinale.payloads.PostRespDTO;
import simone_pierantozzi.u4progettofinale.payloads.UserRespDTO;
import simone_pierantozzi.u4progettofinale.repositories.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public PostRespDTO create(NewPostDTO payload, User author) {
		Post newPost = new Post(payload.text(), LocalDateTime.now(), author);
		Post saved = postRepository.save(newPost);
		return toRespDTO(saved);
	}

	public List<PostRespDTO> findAll() {
		return postRepository.findAll().stream()
				.map(this::toRespDTO)
				.toList();
	}

	public Post findById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Post con id " + id + " non trovato"));
	}

	public PostRespDTO getById(Long id) {
		return toRespDTO(this.findById(id));
	}

	public PostRespDTO update(Long id, NewPostDTO payload, User currentUser) {
		Post post = this.findById(id);

		boolean isAuthor = post.getUser().getId().equals(currentUser.getId());
		boolean isModerator = currentUser.getRole() == Role.MODERATOR;
		if (!isAuthor && !isModerator)
			throw new AccessDeniedException("Solo l'autore del post o un MODERATOR possono modificarlo");

		post.setText(payload.text());
		Post saved = postRepository.save(post);
		return toRespDTO(saved);
	}

	private PostRespDTO toRespDTO(Post post) {
		User author = post.getUser();
		UserRespDTO authorDTO = new UserRespDTO(author.getId(), author.getUsername(), author.getNomeCompleto(), author.getEmail(), author.getRole());
		return new PostRespDTO(post.getId(), post.getText(), post.getPublicationDate(), authorDTO);
	}
}
