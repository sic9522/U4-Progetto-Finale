package simone_pierantozzi.u4progettofinale.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simone_pierantozzi.u4progettofinale.entities.User;
import simone_pierantozzi.u4progettofinale.exceptions.ValidationException;
import simone_pierantozzi.u4progettofinale.payloads.NewPostDTO;
import simone_pierantozzi.u4progettofinale.payloads.PostRespDTO;
import simone_pierantozzi.u4progettofinale.services.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PostRespDTO create(@RequestBody @Validated NewPostDTO payload, BindingResult validationResult,
	                          @AuthenticationPrincipal User currentUser) {
		if (validationResult.hasErrors()) {
			String errorsList = validationResult.getFieldErrors()
					.stream()
					.map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.joining(". "));
			throw new ValidationException(errorsList);
		}

		return this.postService.create(payload, currentUser);
	}

	@GetMapping
	public List<PostRespDTO> findAll() {
		return this.postService.findAll();
	}

	@GetMapping("/{postId}")
	public PostRespDTO getById(@PathVariable Long postId) {
		return this.postService.getById(postId);
	}

	@PutMapping("/{postId}")
	public PostRespDTO update(@PathVariable Long postId, @RequestBody @Validated NewPostDTO payload, BindingResult validationResult,
	                          @AuthenticationPrincipal User currentUser) {
		if (validationResult.hasErrors()) {
			String errorsList = validationResult.getFieldErrors()
					.stream()
					.map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.joining(". "));
			throw new ValidationException(errorsList);
		}

		return this.postService.update(postId, payload, currentUser);
	}
}
