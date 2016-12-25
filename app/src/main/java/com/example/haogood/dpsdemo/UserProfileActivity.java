package com.example.haogood.dpsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        String[] infoContent = new String[]{
//                "哈哈",
//                "哈哈哈",
//                "哈哈哈哈",
//                "哈哈哈哈哈",
//                "哈哈哈哈哈哈"
//        };

        ArrayList<String> myDataset = new ArrayList<>();
        ArrayList<String> infoContent = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            myDataset.add(i + "");
            infoContent.add((i+5)+"");
        }
        MyAdapter myAdapter = new MyAdapter(myDataset, infoContent);
        RecyclerView mList = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mData, mInfo;
//        private List<Car> mlist;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView, mTextView2;
            public ViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.info_text);
                mTextView2 = (TextView) view.findViewById(R.id.info_Title);
            }
        }

        public MyAdapter(List<String> data, List<String> info) {
            mData = data;
            mInfo = info;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.parking_record, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));
            holder.mTextView2.setText(position+"");
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
