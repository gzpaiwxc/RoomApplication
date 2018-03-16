package room.gzp.com.roomapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import room.gzp.com.roomapplication.R;
import room.gzp.com.roomapplication.db.entity.User;
import room.gzp.com.roomapplication.db.manager.AppDatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG="RoomApplication";

    /**插入按钮*/
    private Button btnInsert;
    /**查询按钮*/
    private Button btnQuery;

    /**用户名*/
    private EditText etUserName;
    /**用户年龄*/
    private EditText etUserAge;

    /**显示用户信息的文本*/
    private TextView tvUserInfo;

    private User user;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnInsert = findViewById(R.id.btn_insert);
        btnQuery = findViewById(R.id.btn_query);
        etUserName = findViewById(R.id.et_user_name);
        etUserAge = findViewById(R.id.et_user_age);
        tvUserInfo = findViewById(R.id.tv_user_info);
        btnInsert.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    private void initData() {
        String userName = etUserName.getText().toString();
        String userAge = etUserAge.getText().toString();
        user = new User();
        if (!userName.isEmpty()) {
            user.setName(userName);
        } else {
            Toast.makeText(MainActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (!userAge.isEmpty()) {
            user.setAge(Integer.valueOf(userAge));
        } else {
            Toast.makeText(this, "用户年龄不能为空!", Toast.LENGTH_SHORT).show();
            return ;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                initData();
                insertUser(user);
                break;
            case R.id.btn_query:
                getUsers();
                break;
            default:
                break;
        }
    }

    private void insertUser(User user) {
        Observable.create((ObservableOnSubscribe<User>) e -> {
            AppDatabaseManager.getInstance().insertUser(user);
//            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, "insertUser throwable： " + throwable.toString()))
                .doOnComplete(() -> Log.e(TAG, "onComplete"))
                .subscribe(user1 -> Log.e(TAG, "user1===>" + user1.toString()));

    }

    private void getUsers() {
        Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                users = AppDatabaseManager.getInstance().getUsers();
                e.onNext(users);
//                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable-->" + throwable.toString());
                    }
                })
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        Log.e(TAG, "users===>" + users.toString());
                        StringBuilder sb = new StringBuilder();
                        for (User user : users) {
                            sb.append("userName:").append(user.getName()).append("  Age:").append(user.getAge()).append("\n");
                        }
                        tvUserInfo.setText(sb.toString());
                    }
                });
    }
}
