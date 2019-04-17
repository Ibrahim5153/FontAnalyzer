package com.example.fontanalyzer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fontanalyzer.Models.Detection;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class FinalActivity extends AppCompatActivity {

    String impPath;
    ImageView mImageView;
    TextView contentTextView;
    TextView fontSizeTv;
    TextView fontTv;
    TextView confidenceTv;
    RecyclerView resRecyclerView;
    Detection mDetection;
    String mContent;
    CardView notfoundCV;
    CardView contentCV;
    CardView resultsCV;

    FontViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        viewModel = ViewModelProviders.of(this).get(FontViewModel.class);

        impPath = getIntent().getStringExtra("img_path");

        mImageView = findViewById(R.id.main_image_view);
        contentTextView = findViewById(R.id.content_text_view);
        resRecyclerView = findViewById(R.id.results_rec);
        confidenceTv = findViewById(R.id.confidence_tv);
        fontSizeTv = findViewById(R.id.font_size_tv);
        fontTv = findViewById(R.id.font_tv);
        notfoundCV = findViewById(R.id.notfound_cardView);
        contentCV = findViewById(R.id.content_cv);
        resultsCV = findViewById(R.id.result_cv);
        recoginzeTextFromImage();


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(impPath)
                .into(mImageView);

    }

    void recoginzeTextFromImage(){

        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

        if (!textRecognizer.isOperational()) {

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, "Low Storage", Toast.LENGTH_LONG).show();
            }
        }

        Frame imageFrame = new Frame.Builder()
                .setBitmap(Utility.decodeBitmapFromResource(impPath, 2, 1000, 1000))
                .build();

        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

        String text = "";
        for (int i = 0; i < textBlocks.size(); i++) {

            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));

            text += textBlock.getValue() + "\n";

        }

        mContent = text;

        if(text.equals("")){

            resultsCV.setVisibility(View.GONE);
            contentCV.setVisibility(View.GONE);
            resRecyclerView.setVisibility(View.GONE);
            notfoundCV.setVisibility(View.VISIBLE);
            return;
        }

        viewModel.getDetectionByContent(text).observe(this, new Observer<Detection>() {
            @Override
            public void onChanged(@Nullable Detection detection) {

                if(detection != null){

                    mDetection = detection;
                }else {

                    mDetection = Utility.generateDetection();
                    mDetection.setImgPath(impPath);
                    mDetection.setContent(mContent);
                    viewModel.insertDetection(mDetection);
                }


                resRecyclerView.setAdapter(new ResultsRecAdapter(mDetection.getResultModels(),FinalActivity.this));
                resRecyclerView.setLayoutManager(new LinearLayoutManager(FinalActivity.this,LinearLayoutManager.HORIZONTAL, false));
                fontSizeTv.setText(mDetection.getFontSize());
                fontTv.setText(mDetection.getFontFamily());
                confidenceTv.setText(mDetection.getConfidenceLevel());
                contentTextView.setText(mDetection.getContent());
            }
        });
    }


}
