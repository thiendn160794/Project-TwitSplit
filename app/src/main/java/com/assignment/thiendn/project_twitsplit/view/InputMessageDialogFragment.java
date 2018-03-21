package com.assignment.thiendn.project_twitsplit.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.assignment.thiendn.project_twitsplit.R;
import com.assignment.thiendn.project_twitsplit.model.Message;
import com.assignment.thiendn.project_twitsplit.presenter.AddMessagePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thiendn on 3/21/18.
 */

public class InputMessageDialogFragment extends DialogFragment implements IAddMessageView{

    @BindView(R.id.tv_user_input_message)
    EditText etUserInput;
    @BindView(R.id.tv_show_user)
    TextView tvShowUser;
    @BindView(R.id.btn_save_as_draft)
    Button btnSaveAsDraft;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_tweet)
    Button btnTweet;

    AddMessagePresenter mPresenter;
    MainActivity mActivity;

    public static InputMessageDialogFragment getInstance() {
        return new InputMessageDialogFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_message, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new AddMessagePresenter(this);
        btnSaveAsDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveAsDraft(etUserInput.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancel();
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.tweet(etUserInput.getText().toString());
            }
        });
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        etUserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etUserInput.getText().toString().length() > 0) {
                    btnTweet.setEnabled(true);
                    btnSaveAsDraft.setEnabled(true);
                } else {
                    btnTweet.setEnabled(false);
                    btnSaveAsDraft.setEnabled(false);
                }
                updateCharsCount(etUserInput.getText().toString().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void updateCharsCount(int count) {
        tvShowUser.setText(String.valueOf(count));
        tvShowUser.setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
    }

    @Override
    public void showDraftMessage(String msg) {
        etUserInput.setText(msg);
        etUserInput.setSelection(msg.length());
        btnSaveAsDraft.setEnabled(true);
        btnTweet.setEnabled(true);
    }

    @Override
    public void showErrorMessage() {
        tvShowUser.setText("Input is not valid!");
        tvShowUser.setTextColor(ContextCompat.getColor(mActivity, R.color.red));
    }

    @Override
    public void updateListMessages(Message message) {
        if (mActivity != null) {
            mActivity.onFinishSendMessage(message);
        }
    }

    @Override
    public void hide() {
        getDialog().dismiss();
    }

    public interface IAddTaskDialogListener {
        void onFinishSendMessage(Message message);
    }
}
