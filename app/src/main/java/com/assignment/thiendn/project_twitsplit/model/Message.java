package com.assignment.thiendn.project_twitsplit.model;

import com.github.marlonlom.utilities.timeago.TimeAgo;

/**
 * Created by thiendn on 3/21/18.
 */

public class Message {
    String userName;
    long date;
    String messageContent;

    public Message(String userName, long date, String messageContent) {
        this.userName = userName;
        this.date = date;
        this.messageContent = messageContent;
    }

    public String getUserName() {
        return userName;
    }

    public long getDate() {
        return date;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getTimeAgo() {
        return TimeAgo.using(date);
    }
}
