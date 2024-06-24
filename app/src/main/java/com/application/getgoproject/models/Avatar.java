package com.application.getgoproject.models;

import android.net.Uri;

public class Avatar {
    private int imgAvatar;
    private Uri imgAvatarUri;

    public Avatar(int imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public Avatar(Uri imgAvatarUri) {
        this.imgAvatarUri = imgAvatarUri;
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
}
