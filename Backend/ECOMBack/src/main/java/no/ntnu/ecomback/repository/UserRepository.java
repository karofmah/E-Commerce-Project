package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
