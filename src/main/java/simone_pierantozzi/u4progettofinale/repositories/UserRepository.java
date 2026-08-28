package simone_pierantozzi.u4progettofinale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import simone_pierantozzi.u4progettofinale.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
