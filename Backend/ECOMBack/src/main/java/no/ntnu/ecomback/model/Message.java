package no.ntnu.ecomback.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int messageId;

    @Column
    private String toEmail;
    @Column
    private String fromEmail;
    @Column
    private String messageContent;
    @Column
    private Timestamp timestamp;

    public Message(String toEmail, String fromEmail, String messageContent, Timestamp timestamp) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

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
