package com.example.fontanalyzer.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

@Entity(tableName = "detections")
public class Detection {
    @PrimaryKey(autoGenerate = true)
    long id;

    String imgPath;

    String content;

    String fontSize;

    String fontFamily;

    String confidenceLevel;

    String resultsJson = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public String getResultsJson() {

        if(resultModels != null){
        Gson gson = new Gson();
        String json = gson.toJson(resultModels);
        return json;
        }
        else
        {
            return "";
        }
    }

    public void setResultsJson(String resultsJson) {
        this.resultsJson = resultsJson;
    }

    public ArrayList<ResultModel> getResultModels() {

        if(!resultsJson.equals("")){

            Gson gson = new Gson();
            String jsonInString = resultsJson;
            resultModels = gson.fromJson(jsonInString, new TypeToken<ArrayList<ResultModel>>(){}.getType());
        }

        if(resultModels ==null){
            return new ArrayList<ResultModel>();
        }else
        return resultModels;
    }

    public void setResultModels(ArrayList<ResultModel> resultModels) {
        this.resultModels = resultModels;
    }

    @Ignore
    ArrayList<ResultModel> resultModels;

}
