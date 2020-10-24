package com.example.quizfirestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Sets> arrayList;
    OnListItemClick onListItemClick;

    public interface OnListItemClick{

        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnListItemClick listener){

        onListItemClick = listener;
    }

    public SetsAdapter(Context context, ArrayList<Sets> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_sets, parent, false);

        return new MyViewHolder(view, onListItemClick);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvCuCard.setText(arrayList.get(position).getSetName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCuCard;

        public MyViewHolder(@NonNull View itemView, final OnListItemClick listener) {
            super(itemView);

            tvCuCard = itemView.findViewById(R.id.textViewSetsCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null){

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){

                            listener.onItemClick(v, position);

                        }
                    }
                }
            });

        }
    }


}

