package com.example.associate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.associate.Object.Word;
import com.example.associate.R;

public class WordPageListAdapter extends PagedListAdapter<Word,WordPageListAdapter.MyViewHolder> {

    private static ClickListener clickListener;

    public WordPageListAdapter(@NonNull DiffUtil.ItemCallback<Word> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word item=getItem(position);
        if (item != null){
            holder.bindData(item.getWord());
    }

    }

   public Word getWordAtPosition(int position){
        return getItem(position);
   }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView wordItemView;

        private MyViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view,getAdapterPosition());
                }
            });
        }
        public void bindData(String text) {
            wordItemView.setText(text);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    public interface ClickListener{
        void onItemClick(View view, int position);
    }
}
