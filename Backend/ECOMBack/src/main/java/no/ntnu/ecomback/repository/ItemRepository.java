package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
