package ssu.ssu.huncheckwhatssu;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    private ArrayList<Book> mData = null ;



    public interface OnItemClickListener{
        void onItemClick(View v,int pos);
    }
    private OnItemClickListener mListener=null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView1 ;
        TextView textView2;
        TextView textView3;
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.text1) ;
            textView2 = itemView.findViewById(R.id.text2) ;
            textView3 = itemView.findViewById(R.id.text3) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if(pos!=RecyclerView.NO_POSITION){
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                        if(mListener!=null){mListener.onItemClick(v,pos);}
                    }
                }
            });
            /*아이템 꾸욱 누루면 삭제 가능하도록 */
            itemView.setOnCreateContextMenuListener(this);
        }

        /*아이템 길게 누르면 삭제 가능하도록*/
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Delete=contextMenu.add(Menu.NONE,1,1,"삭제");
            Delete.setOnMenuItemClickListener(onEditList);
        }
        private final MenuItem.OnMenuItemClickListener onEditList= new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1:
                        mData.remove(getAdapterPosition());

                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mData.size());
                        break;
                }
                return true;
            }
        };
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapter2(ArrayList<Book> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        RecyclerViewAdapter2.ViewHolder vh = new RecyclerViewAdapter2.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerViewAdapter2.ViewHolder holder, int position) {

        holder.textView1.setText(mData.get(position).getTitle());
        holder.textView2.setText(mData.get(position).getPrice());
        holder.textView3.setText(mData.get(position).getSeller());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}


