package com.example.apipractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HospViewAdapter extends RecyclerView.Adapter<HospViewAdapter.ViewHolder> {

//    private ArrayList<String> mData = null;
    ArrayList<Item> itemViewArrayList = new ArrayList<Item>(); //객체배열  ?이건 뭐짐..

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hospName, hospAddr;

        ViewHolder(View itemView) {
            super(itemView);

            //뷰 객체에 대한 참조
            hospName = itemView.findViewById(R.id.hospName);
            hospAddr = itemView.findViewById(R.id.hospAddr);
        }

        public void setItem(Item item) {
            hospName.setText(item.getHospitalName());
            hospAddr.setText(item.getHospitalAddr());
        }
    }



    //생성자에서 데이터 리스트 객체를 전달받음.
    HospViewAdapter(ArrayList<Item> list) {
        itemViewArrayList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public HospViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        HospViewAdapter.ViewHolder vh = new HospViewAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(HospViewAdapter.ViewHolder holder, int position) {
        Item item = itemViewArrayList.get(position);
//        String text = mData.get(position);

        holder.setItem(item);
//        holder.hospAddr.setText(text);
    }

    //getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return itemViewArrayList.size();
    }
}
