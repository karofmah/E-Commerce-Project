package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Override
    <S extends Item> S save(S Item);

    List<Item> findByBriefDescriptionContainingOrFullDescriptionContainingOrCategoryContaining(String briefKeyword, String fullKeyword,String category);

    List<Item> findByCategory(Category category);
    @Override
    <S extends Item> List<S> findAll(Example<S> example);
}
