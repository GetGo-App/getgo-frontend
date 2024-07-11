package com.application.getgoproject.models;

import android.net.Uri;

public class ImageNewStatus {
    private Uri image;

    public ImageNewStatus(Uri image) {
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
