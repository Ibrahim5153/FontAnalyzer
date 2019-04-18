package com.example.fontanalyzer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView historyRec;

    FontViewModel viewModel;
    TextView noHistorytv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        noHistorytv = findViewById(R.id.history_tv);

        viewModel = ViewModelProviders.of(this).get(FontViewModel.class);

        historyRec = findViewById(R.id.history_rec);

        historyRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        viewModel.getAllDetections().observe(this, new Observer<List<Detection>>() {
            @Override
            public void onChanged(@Nullable List<Detection> detections) {

                if(detections != null && detections.size() > 0){

                    historyRec.setAdapter(new HistoryRecAdapter(detections, HistoryActivity.this));
                }else {

                    noHistorytv.setVisibility(View.VISIBLE);
                    historyRec.setVisibility(View.GONE);
                }
            }
        });


    }
}
