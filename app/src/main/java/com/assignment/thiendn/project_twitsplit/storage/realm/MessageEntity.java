package com.assignment.thiendn.project_twitsplit.storage.realm;

import com.assignment.thiendn.project_twitsplit.model.Message;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thiendn on 3/21/18.
 */

public class MessageEntity extends RealmObject {

    @PrimaryKey
    long date;
    String userName;
    String messageContent;

    public MessageEntity() {
    }

    public MessageEntity(String userName, long date, String messageContent) {
        this.userName = userName;
        this.date = date;
        this.messageContent = messageContent;
    }

    public void fromDTO(Message message) {
        this.userName = message.getUserName();
        this.date = message.getDate();
        this.messageContent = message.getMessageContent();
    }

    public Message toDTO() {
        return new Message(this.userName, this.date, this.messageContent);
    }
}
