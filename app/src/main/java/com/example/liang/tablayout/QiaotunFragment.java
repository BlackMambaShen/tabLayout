package com.example.liang.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import domain.girlInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiaotunFragment extends BaseFragment {
    private RecyclerView rv_qiaotun;

    @Override
    public void initData() {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(global.Load_Url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<girlInfo> infos = processData(result);
                        rv_qiaotun.setAdapter(new MyAdapter(getContext(),infos));
                        rv_qiaotun.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_qiaotun.setLayoutManager(layoutManager);
                    }
                });
            }
        });
    }

    private ArrayList<girlInfo> processData(String result) {
        try {
            JSONObject jo=new JSONObject(result);
            JSONArray ja = jo.getJSONArray("results");
            ArrayList<girlInfo> infos = new ArrayList<>();
            for (int i = 0; i <ja.length() ; i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                girlInfo info=new girlInfo();
                info.who=jo1.getString("who");
                info.url=jo1.getString("url");
                infos.add(info);
            }
            return infos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_qiaotun, null);
        rv_qiaotun = (RecyclerView)view.findViewById(R.id.rv_qiaotun);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        private LayoutInflater mInflater;
        private Context mContext;
        private ArrayList<girlInfo>mList;
        public MyAdapter(Context context,ArrayList<girlInfo>list){
            mContext=context;
            mInflater=LayoutInflater.from(mContext);
            mList=list;
        }

        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_qiaotun, parent, false);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
                holder.tv_girl.setText(mList.get(position).who);
            Glide.with(mContext).load(mList.get(position).url).into(holder.iv_girl);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView tv_girl;
            ImageView iv_girl;
            public MyHolder(View itemView) {
                super(itemView);
                tv_girl=itemView.findViewById(R.id.tv_girl);
                iv_girl=itemView.findViewById(R.id.iv_girl);
            }
        }
    }
}
