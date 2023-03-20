package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    @Override
    Optional<User> findById(String s);

    <S extends User> S save(S User);
    @Override
    <S extends User> List<S> findAll(Example<S> example);
}
