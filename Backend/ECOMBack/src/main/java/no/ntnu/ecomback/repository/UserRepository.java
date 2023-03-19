package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    <S extends User> S save(S User);
    @Override
    <S extends User> List<S> findAll(Example<S> example);
}
