package com.foglotus.demo.toutiao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.foglotus.demo.toutiao.R;
import com.foglotus.demo.toutiao.TouTiao;
import com.foglotus.demo.toutiao.util.ToastUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.BitmapStream;
import com.zzhoujay.richtext.ig.DefaultImageGetter;
import com.zzhoujay.richtext.ig.ImageDownloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ContentActivity extends AppCompatActivity {

    private TextView content;
    private TextView authorTextView;
    private TextView publishTimeTextView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        initLayout();
        Intent intent = getIntent();
        String  title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String publishTime = intent.getStringExtra("publishTime");
        String articleId = intent.getStringExtra("articleId");
        String token = intent.getStringExtra("token");

        if(token == null || articleId == null){
            ToastUtil.showToast("文章参数不正确");
        }else{
            ToastUtil.showToast("正在获取文章内容");
            titleTextView.setText(title);
            authorTextView.setText(author);
            publishTimeTextView.setText(publishTime);
            getArticle(articleId,token);
        }
    }

    private void initLayout(){
        content = findViewById(R.id.content);
        authorTextView = findViewById(R.id.author);
        publishTimeTextView = findViewById(R.id.publishTime);
        titleTextView = findViewById(R.id.title);
    }

    private void getArticle(String articleId,String token){
        // 请求接口,url根据提供的接口进行设置
        String url = "https://vcapi.lvdaqian.cn/article/"+articleId+"?markdown=true";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).addHeader("Authorization","Bearer "+token).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast("获取文章失败");
                finish();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(response.body().byteStream()));
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    //切换到UI线程，更新视图
                    TouTiao.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            String data = jsonObject.get("data").getAsString();
                            RichText.initCacheDir(TouTiao.getContext());
                            //手动修改图片加载器，从本地assets目录加载
                            RichText.fromMarkdown(data).imageDownloader(new ImageDownloader() {  //重写图片下载器，从本地拿图片
                                @Override
                                public BitmapStream download(String source) throws IOException {
                                    return new BitmapStream() {
                                        InputStream inputStream = getAssets().open(source);
                                        @Override
                                        public InputStream getInputStream() throws IOException {
                                            return inputStream;
                                        }

                                        @Override
                                        public void close() throws IOException {
                                            inputStream.close();
                                        }
                                    };
                                }
                            }).into(content);
                        }
                    });
                }else if(response.code() == 401){
                    ToastUtil.showToast(response.body().string());
                    setResult(403);
                    finish();
                }else{
                    ToastUtil.showToast(response.body().string());
                    finish();
                }
            }
        });
    }
}