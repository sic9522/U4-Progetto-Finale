package simone_pierantozzi.u4progettofinale.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.services.LikeService;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

	private final LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addLike(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
		this.likeService.addLike(postId, currentUser);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping
	public void removeLike(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
		this.likeService.removeLike(postId, currentUser);
	}
}
