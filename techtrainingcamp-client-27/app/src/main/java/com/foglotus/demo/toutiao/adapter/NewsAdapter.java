package com.foglotus.demo.toutiao.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foglotus.demo.toutiao.activity.MainActivity;
import com.foglotus.demo.toutiao.R;
import com.foglotus.demo.toutiao.entity.BaseNews;
import com.foglotus.demo.toutiao.entity.Type0News;
import com.foglotus.demo.toutiao.entity.Type1News;
import com.foglotus.demo.toutiao.entity.Type2News;
import com.foglotus.demo.toutiao.entity.Type3News;
import com.foglotus.demo.toutiao.entity.Type4News;
import com.foglotus.demo.toutiao.util.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //适配器数据来源
    private ArrayList<BaseNews> data;
    //主界面对象，用于各种回调以及跳转
    private MainActivity activity;

    public NewsAdapter(@NonNull ArrayList<BaseNews> data, @NonNull MainActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //创建视图
        //根据类型不同，加载不同的布局
        View view;
        RecyclerView.ViewHolder holder;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_0,parent,false);  //加载视图文件
                holder = new Type0ViewHolder(view);  // 通过holder变量操作视图
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activity.isLogin()){
                            //得到点击的位置，也就是点击了哪个公告，也就是公告数组里的索引
                            int position = holder.getAdapterPosition();
                            activity.gotoArticle(data.get(position));
                        }else{
                            activity.setArticleIndex(holder.getAdapterPosition());//记录因无法登录而无法查看文章的位置
                            activity.gotoLogin();
                        }
                    }
                });
                return holder;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_1,parent,false);
                holder = new Type1ViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activity.isLogin()){
                            //得到点击的位置，也就是点击了哪个公告，也就是公告数组里的索引
                            int position = holder.getAdapterPosition();
                            activity.gotoArticle(data.get(position));
                        }else{
                            activity.setArticleIndex(holder.getAdapterPosition());//记录因无法登录而无法查看文章的位置
                            activity.gotoLogin();
                        }
                    }
                });
                return holder;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_2,parent,false);
                holder = new Type2ViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activity.isLogin()){
                            //得到点击的位置，也就是点击了哪个公告，也就是公告数组里的索引
                            int position = holder.getAdapterPosition();
                            activity.gotoArticle(data.get(position));
                        }else{
                            activity.setArticleIndex(holder.getAdapterPosition());//记录因无法登录而无法查看文章的位置
                            activity.gotoLogin();
                        }
                    }
                });
                return holder;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_3,parent,false);
                holder = new Type3ViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activity.isLogin()){
                            //得到点击的位置，也就是点击了哪个公告，也就是公告数组里的索引
                            int position = holder.getAdapterPosition();
                            activity.gotoArticle(data.get(position));
                        }else{
                            activity.setArticleIndex(holder.getAdapterPosition());//记录因无法登录而无法查看文章的位置
                            activity.gotoLogin();
                        }
                    }
                });
                return holder;
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_4,parent,false);
                holder = new Type4ViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activity.isLogin()){
                            //得到点击的位置，也就是点击了哪个公告，也就是公告数组里的索引
                            int position = holder.getAdapterPosition();
                            activity.gotoArticle(data.get(position));
                        }else{
                            activity.setArticleIndex(holder.getAdapterPosition()); //记录因无法登录而无法查看文章的位置
                            activity.gotoLogin();
                        }
                    }
                });
                return holder;
            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {  //将视图文件和holder对应
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日");
        
        if(holder instanceof Type0ViewHolder){
            Type0News news = (Type0News) data.get(position);
            ((Type0ViewHolder) holder).author.setText(news.getAuthor());
            ((Type0ViewHolder) holder).title.setText(news.getTitle());
            ((Type0ViewHolder) holder).publicTime.setText(simpleDateFormat.format(news.getPublishTime()));
        }else if(holder instanceof Type1ViewHolder){
            Type1News news = (Type1News) data.get(position);
            ((Type1ViewHolder) holder).title.setText(news.getTitle());
            ((Type1ViewHolder) holder).author.setText(news.getAuthor());
            ((Type1ViewHolder) holder).publicTime.setText(simpleDateFormat.format(news.getPublishTime()));
            try {
                InputStream inputStream = activity.getAssets().open(news.getCover());
                ((Type1ViewHolder) holder).cover.setImageDrawable(Drawable.createFromStream(inputStream,null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Type2ViewHolder){
            Type2News news = (Type2News) data.get(position);
            ((Type2ViewHolder) holder).title.setText(news.getTitle());
            ((Type2ViewHolder) holder).author.setText(news.getAuthor());
            ((Type2ViewHolder) holder).publicTime.setText(simpleDateFormat.format(news.getPublishTime()));
            try {
                InputStream inputStream = activity.getAssets().open(news.getCover());
                ((Type2ViewHolder) holder).cover.setImageDrawable(Drawable.createFromStream(inputStream,null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Type3ViewHolder){
            Type3News news = (Type3News) data.get(position);
            ((Type3ViewHolder) holder).title.setText(news.getTitle());
            ((Type3ViewHolder) holder).author.setText(news.getAuthor());
            ((Type3ViewHolder) holder).publicTime.setText(simpleDateFormat.format(news.getPublishTime()));
            try {
                InputStream inputStream = activity.getAssets().open(news.getCover());
                ((Type3ViewHolder) holder).cover.setImageDrawable(Drawable.createFromStream(inputStream,null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Type4ViewHolder){
            Type4News news = (Type4News) data.get(position);
            ((Type4ViewHolder) holder).title.setText(news.getTitle());
            ((Type4ViewHolder) holder).author.setText(news.getAuthor());
            ((Type4ViewHolder) holder).publicTime.setText(simpleDateFormat.format(news.getPublishTime()));
            try {
                InputStream inputStream = activity.getAssets().open(news.getCovers().get(0));
                ((Type4ViewHolder) holder).cover1.setImageDrawable(Drawable.createFromStream(inputStream,null));

                inputStream = activity.getAssets().open(news.getCovers().get(1));
                ((Type4ViewHolder) holder).cover2.setImageDrawable(Drawable.createFromStream(inputStream,null));

                inputStream = activity.getAssets().open(news.getCovers().get(2));
                ((Type4ViewHolder) holder).cover3.setImageDrawable(Drawable.createFromStream(inputStream,null));

                inputStream = activity.getAssets().open(news.getCovers().get(3));
                ((Type4ViewHolder) holder).cover4.setImageDrawable(Drawable.createFromStream(inputStream,null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }  // 获取当前要显示的视图类型

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class Type0ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publicTime;
        public Type0ViewHolder(@NonNull View itemView) {
            super(itemView);
            //初始化
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publicTime = itemView.findViewById(R.id.publishTime);
        }
    }
    static class Type1ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publicTime;
        ImageView cover;
        public Type1ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publicTime = itemView.findViewById(R.id.publishTime);
            cover = itemView.findViewById(R.id.cover);
        }
    }
    static class Type2ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publicTime;
        ImageView cover;
        public Type2ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publicTime = itemView.findViewById(R.id.publishTime);
            cover = itemView.findViewById(R.id.cover);
        }
    }
    static class Type3ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publicTime;
        ImageView cover;
        public Type3ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publicTime = itemView.findViewById(R.id.publishTime);
            cover = itemView.findViewById(R.id.cover);
        }
    }
    static class Type4ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publicTime;
        ImageView cover1;
        ImageView cover2;
        ImageView cover3;
        ImageView cover4;
        public Type4ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publicTime = itemView.findViewById(R.id.publishTime);
            cover1 = itemView.findViewById(R.id.cover1);
            cover2 = itemView.findViewById(R.id.cover2);
            cover3 = itemView.findViewById(R.id.cover3);
            cover4 = itemView.findViewById(R.id.cover4);
        }
    }
}
