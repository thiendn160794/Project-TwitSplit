package com.assignment.thiendn.project_twitsplit.presenter;

import com.assignment.thiendn.project_twitsplit.storage.realm.RealmRepository;
import com.assignment.thiendn.project_twitsplit.view.IListMessageView;

/**
 * Created by thiendn on 3/21/18.
 */

public class MainPresenter {
    IListMessageView view;

    public MainPresenter(IListMessageView view) {
        this.view = view;
        refresh();
    }

    public void onAddButtonClick() {
        view.showAddMessageDialog();
    }

    public void refresh() {
        view.showAllMessage(RealmRepository.getAllMessages());
    }
}
