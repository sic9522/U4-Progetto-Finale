package simone_pierantozzi.u4progettofinale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import simone_pierantozzi.u4progettofinale.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
