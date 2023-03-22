package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
