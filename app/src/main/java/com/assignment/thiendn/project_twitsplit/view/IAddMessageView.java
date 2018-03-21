package com.assignment.thiendn.project_twitsplit.view;

import android.content.Context;

import com.assignment.thiendn.project_twitsplit.model.Message;

/**
 * Created by thiendn on 3/21/18.
 */

public interface IAddMessageView {
    void showDraftMessage(String msg);
    void showErrorMessage();
    void updateListMessages(Message message);
    Context getContext();
    void hide();
}
