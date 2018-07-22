package com.assignment.thiendn.project_twitsplit.view;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.thiendn.project_twitsplit.R;
import com.assignment.thiendn.project_twitsplit.model.Message;
import com.assignment.thiendn.project_twitsplit.presenter.MainPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements IListMessageView, InputMessageDialogFragment.IAddTaskDialogListener{

    @BindView(R.id.rv_list_messages)
    RecyclerView rvListMessages;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    MainPresenter mPresenter;
    ListMessagesAdapter mAdapter;
    ArrayList<Message> mListMessages = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<String> links = new ArrayList<>();
        links.add("http://113.190.253.181:855/AppBackground/Sky/1.jpg");
        links.add("http://113.190.253.181:855/AppBackground/Sky/2.jpg");
        links.add("http://113.190.253.181:855/AppBackground/Sky/3.jpg");
        links.add("http://113.190.253.181:855/AppBackground/Sky/4.jpg");
        links.add("http://113.190.253.181:855/AppBackground/Sky/5.jpg");

        final PublishSubject<Bitmap> subject = PublishSubject.create();
        final BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("sssss");
        Observable<Bitmap> obs = Observable.just(null);

        obs.subscribe(subject);

        subject.subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {

            }
        });


        Observable.zip(Observable.just(links.get(0)), Observable.just(links.get(1)), new BiFunction<String, String, Object>() {
            @Override
            public Object apply(String s, String s2) throws Exception {
                return null;
            }
        });
        compositeDisposable.add(Observable.fromIterable(links)
                .flatMap(new Function<String, ObservableSource<Bitmap>>() {
                    @Override
                    public ObservableSource<Bitmap> apply(final String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
                            @Override
                            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                                if (!emitter.isDisposed()) {
                                    try {
                                        emitter.onNext(Picasso.get().load(s).get());
                                        emitter.onComplete();
                                    } catch (Exception ex) {
                                        emitter.onError(ex);
                                    }
                                }
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext(new Consumer<Bitmap>() {
                                    @Override
                                    public void accept(Bitmap bitmap) throws Exception {
                                        System.out.println("doOnNext: "+ Thread.currentThread());
                                        subject.onNext(bitmap);
                                    }
                                })
                                .doOnComplete(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        System.out.println("onComplete: "+ Thread.currentThread());
                                    }
                                });

                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }));

        compositeDisposable.add(subject.subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {

            }
        }));
        setTitle("Tweeter");
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.onAddButtonClick();
//            }
//        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mAdapter = new ListMessagesAdapter(mListMessages);
        rvListMessages.setLayoutManager(new LinearLayoutManager(this));
        rvListMessages.setAdapter(mAdapter);
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void showAllMessage(ArrayList<Message> messages) {
        mListMessages.clear();
        mListMessages.addAll(messages);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddMessageDialog() {
        FragmentManager fm = getSupportFragmentManager();
        InputMessageDialogFragment inputMessageDialogFragment = InputMessageDialogFragment.getInstance();
        inputMessageDialogFragment.show(fm, "fragment_add_message");
    }

    @Override
    public void onFinishSendMessage(Message message) {
        mListMessages.add(0, message);
        mAdapter.notifyItemInserted(0);
        rvListMessages.smoothScrollToPosition(0);
    }

    class ListMessagesAdapter extends RecyclerView.Adapter<ListMessagesAdapter.MessageViewHolder>{

        ArrayList<Message> mListMessage;

        public ListMessagesAdapter(ArrayList<Message> mListMessage) {
            this.mListMessage = mListMessage;
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            MessageViewHolder viewHolder = new MessageViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            final Message currentMessage = mListMessage.get(position);
            holder.setTvDateTime(String.valueOf(currentMessage.getTimeAgo()));
            holder.setTvUsername(currentMessage.getUserName());
            holder.setTvMessageContain(currentMessage.getMessageContent());
            holder.tvMessageContain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), currentMessage.getMessageContent().length() + "", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListMessage.size();
        }

        class MessageViewHolder extends RecyclerView.ViewHolder {
            ImageView ivAvt;
            TextView tvUsername;
            TextView tvDateTime;
            TextView tvMessageContain;

            public MessageViewHolder(final View itemView) {
                super(itemView);
                ivAvt = itemView.findViewById(R.id.iv_avt);
                tvUsername = itemView.findViewById(R.id.tv_username);
                tvDateTime = itemView.findViewById(R.id.tv_datetime);
                tvMessageContain = itemView.findViewById(R.id.tv_message_contain);
            }

            public void setIvAvt() {
                ivAvt.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_launcher_background));
            }

            public void setTvUsername(String username){
                tvUsername.setText(username);
            }

            public void setTvDateTime(String dateTime){
                tvDateTime.setText(dateTime);
            }

            public void setTvMessageContain(String messageContain){
                tvMessageContain.setText(messageContain);
            }
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
