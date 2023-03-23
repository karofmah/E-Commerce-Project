package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    @Override
    <S extends Bookmark> S save(S entity);
}
