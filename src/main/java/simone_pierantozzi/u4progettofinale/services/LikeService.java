package simone_pierantozzi.u4progettofinale.services;

import org.springframework.stereotype.Service;
import simone_pierantozzi.u4progettofinale.entities.Like;
import simone_pierantozzi.u4progettofinale.entities.Post;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.NotFoundException;
import simone_pierantozzi.u4progettofinale.exceptions.ValidationException;
import simone_pierantozzi.u4progettofinale.repositories.LikeRepository;

@Service
public class LikeService {

	private final LikeRepository likeRepository;
	private final PostService postService;

	public LikeService(LikeRepository likeRepository, PostService postService) {
		this.likeRepository = likeRepository;
		this.postService = postService;
	}

	public void addLike(Long postId, User currentUser) {
		if (likeRepository.existsByUserIdAndPostId(currentUser.getId(), postId))
			throw new ValidationException("Hai già messo like a questo post");

		Post post = postService.findById(postId);
		Like newLike = new Like(currentUser, post);
		likeRepository.save(newLike);
	}

	public void removeLike(Long postId, User currentUser) {
		Like like = likeRepository.findByUserIdAndPostId(currentUser.getId(), postId)
				.orElseThrow(() -> new NotFoundException("Non hai messo like a questo post"));
		likeRepository.delete(like);
	}
}
