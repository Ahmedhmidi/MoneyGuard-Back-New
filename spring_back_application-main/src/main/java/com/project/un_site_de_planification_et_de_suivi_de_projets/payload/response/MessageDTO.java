package com.project.un_site_de_planification_et_de_suivi_de_projets.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
public class MessageDTO {

    private long msg_id;
    private String object;
    private LocalDateTime date;
    private long recipient_id;
    private long sender_id;
    private String senderUserName;
    private String recipientUserName;

    public MessageDTO(long msg_id, String object, LocalDateTime date, long recipient_id, long sender_id, String senderUserName, String recipientUserName) {
        this.msg_id = msg_id;
        this.object = object;
        this.date = date;
        this.recipient_id = recipient_id;
        this.sender_id = sender_id;
        this.senderUserName = senderUserName;
        this.recipientUserName = recipientUserName;
    }

    public MessageDTO(long msg_id, String object, LocalDateTime date, long recipient_id, long sender_id) {
        this.msg_id = msg_id;
        this.object = object;
        this.date = date;
        this.recipient_id = recipient_id;
        this.sender_id = sender_id;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(long recipient_id) {
        this.recipient_id = recipient_id;
    }

    public long getSender_id() {
        return sender_id;
    }

    public void setSender_id(long sender_id) {
        this.sender_id = sender_id;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }
    public MessageDTO() {
    }
}
