package com.example.test;

public class CatData {
    private String information_cat;

    private String image_url;

    private boolean download_state;

    public String getInformation() {
        return information_cat;
    }

    public void setInformation(String information) {
        this.information_cat = information;
    }

    public String getImageUrl() { return image_url; }

    public void setImageUrl(String image) {
        this.image_url = image;
    }

    public boolean getDownloadState(){ return download_state; }

    public void setDownloadState(boolean download_state){ this.download_state = download_state; }
}
