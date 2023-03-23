package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    @Override
    <S extends Bookmark> S save(S entity);

    List<Bookmark> findByUser(User user);
}
