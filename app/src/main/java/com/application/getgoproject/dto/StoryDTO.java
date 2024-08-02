package com.application.getgoproject.dto;

public class StoryDTO {
    private String creator;
    private String content;
    private String caption;

    public StoryDTO(String creator, String content, String caption) {
        this.creator = creator;
        this.content = content;
        this.caption = caption;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
