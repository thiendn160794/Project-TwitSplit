package com.assignment.thiendn.project_twitsplit.presenter;

import com.assignment.thiendn.project_twitsplit.MessageUtils;
import com.assignment.thiendn.project_twitsplit.model.Message;
import com.assignment.thiendn.project_twitsplit.storage.SharePrefRepository;
import com.assignment.thiendn.project_twitsplit.storage.realm.RealmRepository;
import com.assignment.thiendn.project_twitsplit.view.IAddMessageView;

/**
 * Created by thiendn on 3/21/18.
 */

public class AddMessagePresenter {
    IAddMessageView view;

    public AddMessagePresenter(IAddMessageView view) {
        this.view = view;
        String draftMessage = SharePrefRepository.getDraftMessage(view.getContext());
        if (!draftMessage.equals("")) {
            view.showDraftMessage(draftMessage);
        }
    }

    public void saveAsDraft(String msg) {
        SharePrefRepository.storeDraftMessage(view.getContext(), msg);
        view.hide();
    }

    public void tweet(String msg) {
        String[] msgs = MessageUtils.splitMessage(msg);
        if (msgs.length == 0) {
            view.showErrorMessage();
        } else {
            for (int i = msgs.length - 1; i >=0; i--) {
                Message message = new Message("thiendn", System.currentTimeMillis(), msgs[i]);
                RealmRepository.saveMessage(message);
                view.updateListMessages(message);
            }
            view.hide();
        }
        SharePrefRepository.storeDraftMessage(view.getContext(), "");
    }

    public void cancel() {
        SharePrefRepository.storeDraftMessage(view.getContext(), "");
        view.hide();
    }
}
