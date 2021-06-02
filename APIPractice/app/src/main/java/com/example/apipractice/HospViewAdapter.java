package com.example.apipractice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HospViewAdapter extends RecyclerView.Adapter<HospViewAdapter.ViewHolder> {
    Context mContext;
    private OnItemClickListener mListener = null; //리스너 객체 참조를 저장하는 변수
    ArrayList<Item> itemViewArrayList = new ArrayList<Item>(); //객체배열

    public HospViewAdapter(Context context, ArrayList<Item> itemViewArrayList) {
        this.itemViewArrayList = itemViewArrayList;
        this.mContext = context;
    }

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
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        HospViewAdapter.ViewHolder vh = new HospViewAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(HospViewAdapter.ViewHolder holder, int position) {
        Item item = itemViewArrayList.get(position);
        holder.setItem(item);

        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이템 클릭 시 상세보기화면으로 넘어감
                if (position != RecyclerView.NO_POSITION) {
                    //리스너 객체의 메서드 호출
                    Intent intent = new Intent(mContext, MoreInfoActivity.class);
                    Log.d("액티비티", "화면 전환 성공");
                    intent.putExtra("name", item.getHospitalName());
                    Log.d("액티비티", "데이터 보내기");
                    mContext.startActivity(intent);
                }

            }
        });

    }

    //getItemCount() 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return itemViewArrayList.size();
    }

    //커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
