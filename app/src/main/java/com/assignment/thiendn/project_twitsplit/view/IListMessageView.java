package com.assignment.thiendn.project_twitsplit.view;

import com.assignment.thiendn.project_twitsplit.model.Message;

import java.util.ArrayList;

/**
 * Created by thiendn on 3/21/18.
 */

public interface IListMessageView {
    void showAllMessage(ArrayList<Message> messages);
    void showAddMessageDialog();
}
