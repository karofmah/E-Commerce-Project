/**

 The UserRepository interface extends JpaRepository to provide methods for CRUD operations on users.
 */
package no.ntnu.ecomback.repository;
import no.ntnu.ecomback.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Returns an Optional<User> with the given user ID.
     *
     * @param s the ID of the user to retrieve
     * @return an Optional<User> with the given ID, or empty if no such user exists
     */
    @Override
    Optional<User> findById(String s);

    /**
     * Saves a user entity.
     *
     * @param user the user entity to be saved
     * @param <S>  the type of the user entity
     * @return the saved user entity
     */
    <S extends User> S save(S user);

    /**
     * Returns a list of all users matching the given example.
     *
     * @param example the example user to match
     * @param <S>     the type of the example user
     * @return a list of all users matching the given example
     */
    @Override
    <S extends User> List<S> findAll(Example<S> example);
}