package com.example.fontanalyzer;

import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Utility {

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
}
