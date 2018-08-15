package com.example.liang.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JuruFragment extends BaseFragment {
    private RecyclerView rv_juru;
    private ArrayList<String> mdataList;
    private SwipeRefreshLayout swf;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_juru, null);
         rv_juru = (RecyclerView)view.findViewById(R.id.rv_juru);
         swf = (SwipeRefreshLayout)view.findViewById(R.id.swf);
         swf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 List<String> headDatas=new ArrayList<String>();
                 for (int i = 0; i <10 ; i++) {
                     headDatas.add("您配吗？"+i);
                 }
                 myAdapter.AddHeaderItem(headDatas);
                 swf.setRefreshing(false);
             }
         });

         rv_juru.setOnScrollListener(new RecyclerView.OnScrollListener() {
             public int lastVisibleItemPosition;

             @Override
             public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
                 if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItemPosition==myAdapter.getItemCount()){
                     List<String> footDatas=new ArrayList<String>();
                     for (int i = 0; i <10 ; i++) {
                         footDatas.add("我是您的女仆~~"+i);
                     }
                     myAdapter.addFooterItem(footDatas);
                 }

             }

             @Override
             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                  lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
             }
         });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
         myAdapter = new MyAdapter(getContext(), mdataList);
        rv_juru.setAdapter(myAdapter);
        rv_juru.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_juru.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        mdataList=new ArrayList<String>();
        for (int i = 0; i <30 ; i++) {
            mdataList.add("巨乳"+i+"倍大！");
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

         class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_content;
            public ViewHolder(View itemView) {
                super(itemView);
                tv_content= itemView.findViewById(R.id.tv_content);
            }
        }
        private LayoutInflater mInflater;
         private Context mContext;
         private List<String>mDatas;
        public MyAdapter(Context context,List<String>datas){
           this.mContext=context;
           this.mDatas=datas;
           mInflater=LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=mInflater.inflate(R.layout.item_view,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv_content.setText(mDatas.get(position));
            if (position>0){
                holder.tv_content.setTextSize(2*(position+1));
            }
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public void AddHeaderItem(List<String> items){
            mDatas.addAll(0,items);
            notifyDataSetChanged();
        }

        public void addFooterItem(List<String>items){
            mDatas.addAll(items);
            notifyDataSetChanged();
        }

    }
}
