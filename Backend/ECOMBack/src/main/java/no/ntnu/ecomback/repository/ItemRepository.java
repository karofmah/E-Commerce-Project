package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Override
    <S extends Item> S save(S Item);

    @Override
    <S extends Item> List<S> findAll(Example<S> example);
}
