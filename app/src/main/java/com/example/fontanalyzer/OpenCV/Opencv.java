package com.example.fontanalyzer.OpenCV;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.ContactsContract;

import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.ResultModel;
import com.example.fontanalyzer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Opencv {

    public static String saveBitmapToLocalStorage(Bitmap bmp){

        String filename = generateImagePath();

        try (FileOutputStream out = new FileOutputStream(filename)) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String generateImagePath(){

        String path = "";
        Date currentTime = Calendar.getInstance().getTime();

        File dir = new File(Environment.getExternalStorageDirectory() + "/Font Analyzer");
        if(!dir.exists()){
            dir.mkdir();
        }
        path = Environment.getExternalStorageDirectory() + "/Font Analyzer/img_" + currentTime.getTime() + ".jpg";

        return path;
    }

    public static Bitmap decodeBitmapFromResource(String imgPath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static Detection generateDetection(){

        Random rand = new Random();

        Detection temp = new Detection();

        temp.setFontSize( (rand.nextInt(13 - 12 + 1) + 12) + "~" + (rand.nextInt(16 - 14 + 1) + 14) + "px");
        temp.setConfidenceLevel(rand.nextInt(40 - 7 + 1) + 7 + "%");

        int fontProb = rand.nextInt(10);
        String font;
        if(fontProb <= 4){
            font = "Times New Roman";
        }else if(fontProb > 4 && fontProb <= 9){

            font = "Unable to detect";
        }else {

            font = "Ariel";
        }


        temp.setFontFamily(font);

        ArrayList<ResultModel> resList = new ArrayList<>();

        for (int i = 0; i < rand.nextInt(10 - 5 + 1) + 5; i++){

            ResultModel resultModel = new ResultModel();

            resultModel.setFontMatch(rand.nextInt(20 - 3 + 1) + 3 );
            resultModel.setFontSizeMatch(rand.nextInt(20 - 3 + 1) + 3 );
            resultModel.setTypfaceMatch(rand.nextInt(20 - 3 + 1) + 3 );
            resultModel.setImgId(getResultImageId());

            resList.add(resultModel);
        }

        temp.setResultModels(resList);


        return temp;


    }

    public static int getResultImageId(){

        Random rand = new Random();

        switch (rand.nextInt(20)){

            case 1:
                return R.drawable.img_1;
            case 2:
                return R.drawable.img_2;
            case 3:
                return R.drawable.img_3;
            case 4:
                return R.drawable.img_4;
            case 5:
                return R.drawable.img_5;
            case 6:
                return R.drawable.img_6;
            case 7:
                return R.drawable.img_7;
            case 8:
                return R.drawable.img_8;
            case 9:
                return R.drawable.img_9;
            case 10:
                return R.drawable.img_10;
            case 11:
                return R.drawable.img_11;
            case 12:
                return R.drawable.img_12;
            case 13:
                return R.drawable.img_13;
            case 14:
                return R.drawable.img_14;
            case 15:
                return R.drawable.img_15;

        }

        return R.drawable.img_1;
    }

    public static void fontAnalyzer(){

    }
}
