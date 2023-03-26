package no.ntnu.ecomback.repository;

import no.ntnu.ecomback.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByToEmailAndFromEmailOrderByTimestamp(String toEmail, String fromEmail);

    List<Message> findByToEmailAndFromEmailOrToEmailAndFromEmailOrderByTimestamp(
                String toEmail1, String fromEmail1, String toEmail2, String fromEmail2);

    List<Message> findByFromEmail(String fromEmail);
    List<Message> findByToEmail(String fromEmail);


    void deleteAllByFromEmailOrToEmail(String fromEmail, String toEmail);
}
