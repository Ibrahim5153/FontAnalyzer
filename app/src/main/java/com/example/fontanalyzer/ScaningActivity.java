package com.example.fontanalyzer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fontanalyzer.OpenCV.Opencv;

import java.io.File;
import java.io.InputStream;

public class ScaningActivity extends AppCompatActivity {

    CardView galleryCard;
    CardView cameraCard;

    int PICK_IMAGE_GALLERY = 0;
    int PICK_IMAGE_CAMERA = 1;
    Uri cameraImgUri;
    File imgFile;

    Toolbar toolbar;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaning);

//        toolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);

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

                ;//Uri.fromFile(new File(Opencv.generateImagePath()));
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imgFile = new File(Opencv.generateImagePath());
                cameraImgUri =  FileProvider.getUriForFile(ScaningActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",imgFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImgUri);
                startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        dialog = ProgressDialog.show(ScaningActivity.this, "",
                "Analyzing. Please wait...", true);
        dialog.setCancelable(false);
        final Intent intent = new Intent(this, FinalActivity.class);

        if (requestCode == PICK_IMAGE_GALLERY) {
            //TODO: action
            try {


                final Uri imageUri = data.getData();
                final InputStream imageStream;
                //imageStream = getContentResolver().openInputStream(imageUri);
                //final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                intent.putExtra("img_path",getRealPathFromURI_API19(this, imageUri));//Opencv.saveBitmapToLocalStorage(selectedImage));


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                         startActivity(intent);


                    }
                }, 5000);

            } catch (Exception e) {
                dialog.dismiss();
            }


        }else if(requestCode == PICK_IMAGE_CAMERA){

            try {
                intent.putExtra("img_path", imgFile.getAbsolutePath());

                if(!imgFile.exists()){

                    dialog.dismiss();
                    return;
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        startActivity(intent);


                    }
                }, 5000);

            }catch (Exception ex){
                dialog.dismiss();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog != null)
        dialog.dismiss();
    }

    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {

            new AlertDialog.Builder(this)
                    .setTitle("Alert!")
                    .setMessage("Do you really want to logout?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                            editor.putBoolean("logged_in", false);
                            editor.apply();
                            startActivity(new Intent(ScaningActivity.this, AuthActivity.class));
                            finish();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

            return true;
        }else if(id == R.id.action_history){

            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
