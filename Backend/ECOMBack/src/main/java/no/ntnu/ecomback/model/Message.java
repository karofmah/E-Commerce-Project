package no.ntnu.ecomback.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class Message {

    /**
     * The id of the message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The id of the message")
    private int messageId;

    /**
     * The email of the recipient
     */
    @Column
    @Schema(description = "The email of the recipient")
    private String toEmail;

    /**
     * The email of the sender
     */
    @Column
    @Schema(description = "The email of the sender")
    private String fromEmail;

    /**
     * The content of the message
     */
    @Column
    @Schema(description = "The content of the message")
    private String messageContent;

    /**
     * The timestamp of the message
     */
    @Column
    @Schema(description = "The timestamp of the message")
    private Timestamp timestamp;

    /**
     * Instantiates a new message
     * @param toEmail the recipient email
     * @param fromEmail the sender email
     * @param messageContent the content of the message
     * @param timestamp the current timestamp
     */
    public Message(String toEmail, String fromEmail, String messageContent, Timestamp timestamp) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    /**
     * Instantiates a new Message.
     */
    public Message() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Gets message id.
     * @return the message id
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Sets message id.
     * @param messageId the message id
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets to email.
     * @return the to email
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * Sets to email.
     * @param toEmail the to email
     */
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    /**
     * Gets from email.
     * @return the from email
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Sets from email.
     * @param fromEmail the from email
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * Gets message content.
     * @return the message content
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Sets message content.
     * @param messageContent the message content
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * Gets timestamp.
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     * @param timestamp the timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", toEmail='" + toEmail + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
