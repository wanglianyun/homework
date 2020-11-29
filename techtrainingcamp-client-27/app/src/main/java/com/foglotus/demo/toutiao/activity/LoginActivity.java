package com.foglotus.demo.toutiao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.foglotus.demo.toutiao.R;
import com.foglotus.demo.toutiao.entity.User;
import com.foglotus.demo.toutiao.util.JsonUtil;
import com.foglotus.demo.toutiao.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class LoginActivity extends AppCompatActivity {

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    private Button loginBtn;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private boolean isLoginNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
    }

    private void initLayout(){
        loginBtn = findViewById(R.id.login);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isLoginNow) return;  // 登录保护机制
                ToastUtil.showToast("正在登录，请稍后");
                isLoginNow = true;
                login();
            }
        });
    }

    private void login(){

        User user = new User();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        String dataJson = JsonUtil.toJson(user);  // 转成json格式
        OkHttpClient client = new OkHttpClient();  //创建客户端
        RequestBody requestBody = RequestBody.create(dataJson,JSON);
        Request request = new Request.Builder().url("https://vcapi.lvdaqian.cn/login").post(requestBody).build();
        Call call = client.newCall(request);  //回车访问
        call.enqueue(new Callback() {  // 把请求添加到队列
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("当前token", "失败了");
                isLoginNow = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(response.body().byteStream()));
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    Log.d("当前token", jsonObject.get("token").getAsString());
                    Intent intent = new Intent();
                    intent.putExtra("token",jsonObject.get("token").getAsString());  // 返回token
                    setResult(1, intent);
                }else{
                    Log.d("失败", response.body().toString());
                    setResult(0, null);
                }
                isLoginNow = false;
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {  //监听返回键
        super.onBackPressed();
        setResult(0, null);
        finish();
    }
}