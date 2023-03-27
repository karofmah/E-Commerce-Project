/**

 The MessageRepository interface extends JpaRepository to provide methods for CRUD operations on messages.
 */
package no.ntnu.ecomback.repository;
import no.ntnu.ecomback.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Returns a list of messages between two users, ordered by timestamp.
     *
     * @param toEmail   the email address of the recipient
     * @param fromEmail the email address of the sender
     * @return a list of messages between the two users, ordered by timestamp
     */
    List<Message> findByToEmailAndFromEmailOrderByTimestamp(String toEmail, String fromEmail);

    /**
     * Returns a list of messages between two pairs of users, ordered by timestamp.
     *
     * @param toEmail1   the email address of the first recipient
     * @param fromEmail1 the email address of the first sender
     * @param toEmail2   the email address of the second recipient
     * @param fromEmail2 the email address of the second sender
     * @return a list of messages between the two pairs of users, ordered by timestamp
     */
    List<Message> findByToEmailAndFromEmailOrToEmailAndFromEmailOrderByTimestamp(
        String toEmail1, String fromEmail1, String toEmail2, String fromEmail2);

    /**
     * Returns a list of messages sent by a user.
     *
     * @param fromEmail the email address of the sender
     * @return a list of messages sent by the user
     */
    List<Message> findByFromEmail(String fromEmail);

    /**
     * Returns a list of messages received by a user.
     *
     * @param fromEmail the email address of the recipient
     * @return a list of messages received by the user
     */
    List<Message> findByToEmail(String fromEmail);

    /**
     * Deletes all messages sent or received by a pair of users.
     *
     * @param fromEmail the email address of one user
     * @param toEmail   the email address of the other user
     */
    void deleteAllByFromEmailOrToEmail(String fromEmail, String toEmail);
}