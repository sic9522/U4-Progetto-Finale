package simone_pierantozzi.u4progettofinale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import simone_pierantozzi.u4progettofinale.entities.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

	boolean existsByUserIdAndPostId(Long userId, Long postId);

	Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
}
