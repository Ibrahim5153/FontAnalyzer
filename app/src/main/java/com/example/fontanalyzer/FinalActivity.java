package com.example.fontanalyzer;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class FinalActivity extends AppCompatActivity {

    String impPath;
    ImageView mImageView;
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        impPath = getIntent().getStringExtra("img_path");
        mImageView = findViewById(R.id.main_image_view);
        contentTextView = findViewById(R.id.content_text_view);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(impPath)
                .into(mImageView);

        recoginzeTextFromImage();
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
        contentTextView.setText(text);
    }
}
