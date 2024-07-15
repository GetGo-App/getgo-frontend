package com.application.getgoproject.dto;

import java.util.List;

public class StatusDTO {
    private String content;
    private List<String> images;
    private String uploader;
    private String privacyMode;

    public StatusDTO() {
    }

    public StatusDTO(String content, List<String> images, String uploader, String privacyMode) {
        this.content = content;
        this.images = images;
        this.uploader = uploader;
        this.privacyMode = privacyMode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(String privacyMode) {
        this.privacyMode = privacyMode;
    }
}
