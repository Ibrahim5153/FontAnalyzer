package com.example.fontanalyzer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.ResultModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecAdapter extends RecyclerView.Adapter<HistoryRecAdapter.HistoryRecViewHolder> {
    List<Detection> data;
    Context context;

    public HistoryRecAdapter(List<Detection> resultModels, Context context){

        data = resultModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryRecAdapter.HistoryRecViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryRecAdapter.HistoryRecViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_rec_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull  HistoryRecAdapter.HistoryRecViewHolder  viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(data.get(i).getImgPath())
                .into(viewHolder.resImgView);

        viewHolder.fontSizeTv.setText(data.get(i).getFontSize());
        viewHolder.confidenceTv.setText(data.get(i).getConfidenceLevel());
        viewHolder.fontTv.setText(data.get(i).getFontFamily());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HistoryRecViewHolder extends RecyclerView.ViewHolder{

        public ImageView resImgView;
        public TextView confidenceTv;
        public TextView fontTv;
        public TextView fontSizeTv;

        public HistoryRecViewHolder(@NonNull View itemView) {
            super(itemView);

            resImgView = itemView.findViewById(R.id.rec_item_iv);
            confidenceTv = itemView.findViewById(R.id.confidence_result_tv);
            fontTv = itemView.findViewById(R.id.font_result_tv);
            fontSizeTv = itemView.findViewById(R.id.font_size_result_tv);

        }
    }
}
