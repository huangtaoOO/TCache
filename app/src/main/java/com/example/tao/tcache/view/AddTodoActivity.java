package com.example.tao.tcache.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.example.tao.tcache.R;
import com.example.tao.tcache.api.Api;
import com.example.tao.tcache.base.NetworkClient;
import com.example.tao.tcache.base.Reply;
import com.example.tao.tcache.model.TodoListActivityModel;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddTodoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.button)
    Button button;

    private TodoListActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        ButterKnife.bind(this);
        model = new TodoListActivityModel();
    }
    @OnClick({R.id.back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.button:
                model.add(title.getText().toString(),
                        content.getText().toString(),
                        time.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Reply>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Reply todoBeanReply) {
                               if (todoBeanReply.getErrorCode()==0){
                                   Toast.makeText(AddTodoActivity.this,"成功",Toast.LENGTH_SHORT).show();
                                   finish();
                               }else {
                                   Toast.makeText(AddTodoActivity.this,todoBeanReply.getErrorMsg(),Toast.LENGTH_SHORT).show();
                               }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(AddTodoActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.i();
                            }
                        });
                break;
        }
    }
}
