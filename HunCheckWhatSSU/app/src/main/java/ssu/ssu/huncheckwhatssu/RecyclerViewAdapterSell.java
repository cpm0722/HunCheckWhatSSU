package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterSell extends RecyclerView.Adapter<RecyclerViewAdapterSell.ViewHolder> {
    private ArrayList<Book> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;
        TextView textView2;
        TextView textView3;
        ViewHolder(View itemView) {
            super(itemView) ;
            textView1 = itemView.findViewById(R.id.text1) ;
            textView2 = itemView.findViewById(R.id.text2) ;
            textView3 = itemView.findViewById(R.id.tv_seller) ;
        }
    }

    RecyclerViewAdapterSell(ArrayList<Book> list) {
        mData = list ;
    }

      @Override
    public RecyclerViewAdapterSell.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item_s, parent, false) ;
        RecyclerViewAdapterSell.ViewHolder vh = new RecyclerViewAdapterSell.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerViewAdapterSell.ViewHolder holder, int position) {

        holder.textView1.setText(mData.get(position).getTitle());
        holder.textView2.setText(mData.get(position).getPrice());
        holder.textView3.setText(mData.get(position).getSeller());
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}