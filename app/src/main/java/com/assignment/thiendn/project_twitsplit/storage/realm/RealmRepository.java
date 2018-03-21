package com.assignment.thiendn.project_twitsplit.storage.realm;

import com.assignment.thiendn.project_twitsplit.model.Message;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by thiendn on 3/21/18.
 */

public class RealmRepository {
    public static void clearRealmDB() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // delete all realm objects
        realm.deleteAll();

        //commit realm changes
        realm.commitTransaction();
    }

    public static void saveMessage(Message message) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            MessageEntity entity = new MessageEntity();
            entity.fromDTO(message);
            realm.insertOrUpdate(entity);
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public static ArrayList<Message> getAllMessages() {
        Realm realm = null;
        ArrayList<Message> listMessages = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<MessageEntity> results = realm.where(MessageEntity.class).findAll().sort("date", Sort.DESCENDING);
            for (MessageEntity entity : results) {
                listMessages.add(entity.toDTO());
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return listMessages;
    }
}
