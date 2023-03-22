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

    @Query("SELECT i FROM Item i WHERE i.briefDescription LIKE %:keyword% OR i.fullDescription LIKE %:keyword% OR i.category.categoryName LIKE %:keyword%")
    List<Item> findByKeyword(String keyword);
    List<Item> findByCategory(Category category);
    @Override
    <S extends Item> List<S> findAll(Example<S> example);
}
