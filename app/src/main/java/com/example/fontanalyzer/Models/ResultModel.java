package com.example.fontanalyzer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel {

    @SerializedName("imgid")
    @Expose
    private int imgId;

    @SerializedName("typefacematch")
    @Expose
    private float typfaceMatch;

    @SerializedName("fontsizematch")
    @Expose
    private float fontSizeMatch;

    @SerializedName("fontmatch")
    @Expose
    private float fontMatch;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public float getTypfaceMatch() {
        return typfaceMatch;
    }

    public void setTypfaceMatch(float typfaceMatch) {
        this.typfaceMatch = typfaceMatch;
    }

    public float getFontSizeMatch() {
        return fontSizeMatch;
    }

    public void setFontSizeMatch(float fontSizeMatch) {
        this.fontSizeMatch = fontSizeMatch;
    }

    public float getFontMatch() {
        return fontMatch;
    }

    public void setFontMatch(float fontMatch) {
        this.fontMatch = fontMatch;
    }
}
