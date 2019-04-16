package com.example.fontanalyzer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ScaningActivity extends AppCompatActivity {

    CardView galleryCard;
    CardView cameraCard;

    int PICK_IMAGE_GALLERY = 0;
    int PICK_IMAGE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaning);

        galleryCard = findViewById(R.id.cardView_gallery);
        galleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);

            }
        });
        cameraCard = findViewById(R.id.cardView_camera);
        cameraCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE_GALLERY) {
            //TODO: action
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;

                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Intent intent = new Intent(this, FinalActivity.class);
                intent.putExtra("img_path",Utility.saveBitmapToLocalStorage(selectedImage));
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else if(requestCode == PICK_IMAGE_CAMERA){

            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(this, FinalActivity.class);
                intent.putExtra("img_path",Utility.saveBitmapToLocalStorage(photo));
                startActivity(intent);
            }catch (Exception ex){

            }

        }
    }


}
