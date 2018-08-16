package com.example.liang.tablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.girlInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiaotunFragment extends BaseFragment {
    private RecyclerView rv_qiaotun;
    private MyAdapter myAdapter;
    private int count=0;
    private ArrayList<ImageView> list;
    private Handler handler;
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
                Log.d("QiaotunFragment",result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<girlInfo.PicData> picData = processData(result);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_qiaotun.setLayoutManager(layoutManager);
                        rv_qiaotun.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                         myAdapter = new MyAdapter(getContext(), picData);
                        rv_qiaotun.setAdapter(myAdapter);
                    }
                });
            }
        });
    }

    private ArrayList<girlInfo.PicData> processData(String result) {
        try {
//            JSONObject jo=new JSONObject(result);
//            JSONArray ja = jo.getJSONArray("data");
//            System.out.println("ja"+ja);
//            ArrayList<girlInfo> infos = new ArrayList<girlInfo>();
//            for (int i = 0; i <ja.length() ; i++) {
//                JSONObject jo1 = ja.getJSONObject(i);
//                System.out.println(jo1);
//                girlInfo info=new girlInfo();
//                info.abs=jo1.getString("abs");
//                info.image_url=jo1.getString("image_url");
//                infos.add(info);
//            }
//            System.out.println("数据："+infos.toString());
            Gson gson=new Gson();
            girlInfo girlInfo = gson.fromJson(result, girlInfo.class);
            return girlInfo.data;
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
        rv_qiaotun.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItemPosition+1==myAdapter.getItemCount()){
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("http://image.baidu.com/channel/listjson?pn="+count+"&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ftags=%E6%A0%A1%E8%8A%B1&ie=utf8").build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String result = response.body().string();
                            final ArrayList<girlInfo.PicData> picData = processData(result);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myAdapter.mList.addAll(picData);
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
                count++;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv_qiaotun.getLayoutManager();
                 lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int[] arr = {R.drawable.pre19, R.drawable.after19, R.drawable.light, R.drawable.old};
        list=new ArrayList<ImageView>();
        for (int i = 0; i <4 ; i++) {
            ImageView view=new ImageView(getContext());
            view.setImageResource(arr[i]);
            list.add(view);
        }
        initData();
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private LayoutInflater mInflater;
        private Context mContext;
        private ArrayList<girlInfo.PicData>mList;
        public MyAdapter(Context context,ArrayList<girlInfo.PicData>list){
            mContext=context;
            mInflater=LayoutInflater.from(mContext);
            mList=list;
        }
        public static final int ITEM_TYPE_IMAGE=0;
        public static final int ITEM_TYPE_TEXT=1;
        public static final int ITEM_TYPE_VP=2;

        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case ITEM_TYPE_IMAGE:
                    View view = mInflater.inflate(R.layout.item_qiaotun, parent, false);
                    MyHolder myHolder = new MyHolder(view);
                    return myHolder;
                case ITEM_TYPE_TEXT:
                    View view1 = mInflater.inflate(R.layout.item_text, parent, false);
                    MyTextHolder myTextHolder = new MyTextHolder(view1);
                    return myTextHolder;
                case ITEM_TYPE_VP:
                    View view2 = mInflater.inflate(R.layout.item_vp, parent, false);
                    MyViewPagerHolder myViewPagerHolder=new MyViewPagerHolder(view2);
                    return myViewPagerHolder;
            }
            return null;
        }

        @SuppressLint("HandlerLeak")
        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof MyHolder){
                ((MyHolder)holder).tv_girl.setText(mList.get(position).abs);
                Glide.with(mContext).load(mList.get(position).image_url).into(((MyHolder)holder).iv_girl);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),GirlActivity.class);
                        intent.putExtra("abs",mList.get(position).abs);
                        intent.putExtra("image_url",mList.get(position).image_url);
                        startActivity(intent);
                    }
                });
            }else if (holder instanceof MyTextHolder){
                ((MyTextHolder) holder).tv_text.setText("沈龙昊，我真的好喜欢你！和我在一起吧！");
            }else if (holder instanceof MyViewPagerHolder){
                ((MyViewPagerHolder) holder).vp_girl.setAdapter(new MyViewAdapter());
                if (handler==null){
                    handler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            int currentItem = ((MyViewPagerHolder)holder).vp_girl.getCurrentItem();
                            currentItem++;
                            if (currentItem>list.size()-1){
                                currentItem=0;
                            }
                            ((MyViewPagerHolder) holder).vp_girl.setCurrentItem(currentItem);
                            handler.sendEmptyMessageDelayed(0,5000);
                        }
                    };
                    handler.sendEmptyMessageDelayed(0,5000);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0){
                return ITEM_TYPE_VP;
            }else if (position>0&&position<5){
                return ITEM_TYPE_TEXT;
            }else {
                return ITEM_TYPE_IMAGE;
            }
        }

        public void addFootItem(List<girlInfo.PicData>item){
            mList.addAll(item);
            notifyDataSetChanged();

        }
        class MyHolder extends RecyclerView.ViewHolder{
            TextView tv_girl;
            ImageView iv_girl;
            public MyHolder(View itemView) {
                super(itemView);
                tv_girl=(TextView) itemView.findViewById(R.id.tv_girl);
                iv_girl=(ImageView) itemView.findViewById(R.id.iv_girl);
            }
        }

        class MyTextHolder extends RecyclerView.ViewHolder{
            TextView tv_text;
            public MyTextHolder(View itemView) {
                super(itemView);
                tv_text=(TextView) itemView.findViewById(R.id.tv_text);
            }
        }

        class MyViewPagerHolder extends RecyclerView.ViewHolder{
            ViewPager vp_girl;
            public MyViewPagerHolder(View itemView) {
                super(itemView);
                vp_girl=(ViewPager) itemView.findViewById(R.id.vp_girl);
            }
        }

        class MyViewAdapter extends PagerAdapter{

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView view = list.get(position);
                container.addView(view);
                return view;
            }
        }
    }
}
