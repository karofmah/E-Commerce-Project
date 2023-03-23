package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Override
    <S extends Item> S save(S Item);

    List<Item> findByBriefDescriptionContainingOrFullDescriptionContainingOrCategory_CategoryNameContaining(String briefKeyword, String fullKeyword,String categoryKeyword);

    List<Item> findByCategory(Category category);
    @Override
    <S extends Item> List<S> findAll(Example<S> example);
}
