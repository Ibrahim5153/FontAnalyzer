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
import com.example.fontanalyzer.Models.ResultModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultsRecAdapter extends RecyclerView.Adapter<ResultsRecAdapter.ResultRecViewHolder> {

    ArrayList<ResultModel> data;
    Context context;

    public ResultsRecAdapter(ArrayList<ResultModel> resultModels, Context context){

        data = resultModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultRecViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ResultRecViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.results_rec_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ResultRecViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(data.get(i).getImgId())
                .into(viewHolder.resImgView);

        viewHolder.fontSizeTv.setText(data.get(i).getFontSizeMatch() + "%");
        viewHolder.fonttv.setText(data.get(i).getFontMatch() + "%");
        viewHolder.typeFaceTv.setText(data.get(i).getTypfaceMatch() + "%");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ResultRecViewHolder extends RecyclerView.ViewHolder{

        public ImageView resImgView;
        public TextView typeFaceTv;
        public TextView fonttv;
        public TextView fontSizeTv;

        public ResultRecViewHolder(@NonNull View itemView) {
            super(itemView);

            resImgView = itemView.findViewById(R.id.rec_item_iv);
            typeFaceTv = itemView.findViewById(R.id.tepface_match_tv);
            fonttv = itemView.findViewById(R.id.font_match_tv);
            fontSizeTv = itemView.findViewById(R.id.font_size_match_tv);

        }
    }
}
