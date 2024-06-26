package com.application.getgoproject.models;

import android.graphics.Bitmap;
import android.net.Uri;

public class Avatar {
    private int imgAvatar;
    private Uri imgAvatarUri;
    private Bitmap bitmap;

    public Avatar(int imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public Avatar(Uri imgAvatarUri) {
        this.imgAvatarUri = imgAvatarUri;
    }

    public Avatar(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(int imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public Uri getImgAvatarUri() {
        return imgAvatarUri;
    }

    public void setImgAvatarUri(Uri imgAvatarUri) {
        this.imgAvatarUri = imgAvatarUri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
