package com.assignment.thiendn.project_twitsplit.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by thiendn on 3/21/18.
 */

public class SharePrefRepository {

    private final static String KEY_DRAFT_MESSAGE = "key_draft_message";

    private SharePrefRepository() {
    }

    private static SharedPreferences getSharedPreference(Context ct) {
        return PreferenceManager.getDefaultSharedPreferences(ct);
    }

    private static void writeString(Context context, String name, String value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void storeDraftMessage(Context context, String draftMessage) {
        writeString(context, KEY_DRAFT_MESSAGE, draftMessage);
    }

    public static String getDraftMessage(Context context) {
        return getSharedPreference(context).getString(KEY_DRAFT_MESSAGE, "");
    }
}
