package com.foglotus.demo.toutiao.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.foglotus.demo.toutiao.R;
import com.foglotus.demo.toutiao.adapter.NewsAdapter;
import com.foglotus.demo.toutiao.entity.BaseNews;
import com.foglotus.demo.toutiao.entity.Type0News;
import com.foglotus.demo.toutiao.entity.Type1News;
import com.foglotus.demo.toutiao.entity.Type2News;
import com.foglotus.demo.toutiao.entity.Type3News;
import com.foglotus.demo.toutiao.entity.Type4News;
import com.foglotus.demo.toutiao.util.JsonUtil;
import com.foglotus.demo.toutiao.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //列表控件
    private RecyclerView recyclerView;

    private TextView contentNotice;

    private LinearLayoutManager layoutManager;

    private NewsAdapter adapter;

    private ArrayList<BaseNews> data;

    private String token;

    private int articleIndex; //

    public int getArticleIndex() {
        return articleIndex;
    }

    public void setArticleIndex(int articleIndex) {
        this.articleIndex = articleIndex;
    }

    private static final int REQUEST_CODE_LOGIN = 1022; //判断从那个页面回来
    private static final int REQUEST_CODE_ARTICLE = 1023;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据
        initData();
        initLayout();
        SharedPreferences sharedPreferences= getSharedPreferences("data",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        getData();

    }

    private void initData(){
        data = new ArrayList<BaseNews>();
        adapter = new NewsAdapter(data,this);
    }

    public void getData(){
        contentNotice.setText("正在获取公告");
        contentNotice.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        data.clear();
        try {
            InputStream metadataStream = getAssets().open("metadata.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(metadataStream));
            JsonArray jsonElements = JsonParser.parseReader(reader).getAsJsonArray();
            for(JsonElement jsonElement:jsonElements){
                JsonObject object = jsonElement.getAsJsonObject();
                int type = Integer.parseInt(String.valueOf(object.get("type")));
                switch (type){
                    case 0:
                        data.add(JsonUtil.toObject(jsonElement.toString(),Type0News.class));
                        break;
                    case 1:
                        data.add(JsonUtil.toObject(jsonElement.toString(),Type1News.class));
                        break;
                    case 2:
                        data.add(JsonUtil.toObject(jsonElement.toString(),Type2News.class));
                        break;
                    case 3:
                        data.add(JsonUtil.toObject(jsonElement.toString(),Type3News.class));
                        break;
                    case 4:
                        data.add(JsonUtil.toObject(jsonElement.toString(),Type4News.class));
                        break;
                    default:
                        throw new RuntimeException("解析数据错误");
                }
            }
            Collections.sort(data,new Comparator<BaseNews>(){
                @Override
                public int compare(BaseNews o1,BaseNews o2) {
                    return o1.getType() - o2.getType();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
        contentNotice.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void initLayout(){
        recyclerView = findViewById(R.id.recycler_view);
        contentNotice = findViewById(R.id.content_notice);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 给每一个子项目加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        contentNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ToastUtil.showToast(String.valueOf(view.getId()));
    }

    public boolean isLogin(){
        return token != null;
    }

    public void gotoLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivityForResult(intent,REQUEST_CODE_LOGIN);
    }

    public void gotoArticle(BaseNews news){
        Intent intent = new Intent(this,ContentActivity.class);
        intent.putExtra("title",news.getTitle());
        intent.putExtra("author",news.getAuthor());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日");
        intent.putExtra("publishTime",simpleDateFormat.format(news.getPublishTime()));
        intent.putExtra("articleId",news.getId());
        intent.putExtra("token",token);
        startActivityForResult(intent,REQUEST_CODE_ARTICLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOGIN){
            if(resultCode == 1){
                token = data.getStringExtra("token");
                ToastUtil.showToast("登录成功");
                Log.d("token",token);
                SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token);
                editor.apply();
                if(articleIndex != -1){
                    gotoArticle(this.data.get(articleIndex));
                }
            }else{
                ToastUtil.showToast("登录失败");
            }
        }else if(requestCode == REQUEST_CODE_ARTICLE){
           if(resultCode == 403){
               token = null;
           }else{
               articleIndex = -1;
           }
        }
    }
}