package com.myprojects.androidlessons.sportclubmanager.model;

import androidx.annotation.NonNull;

public class TextTemplate {

    private int textTemplateId;

    private String templateMessage;

    public TextTemplate(int textTemplateId, String template) {
        this.textTemplateId = textTemplateId;
        this.templateMessage = template;
    }

    public String getTemplateMessage() {
        return templateMessage;
    }

    public void setTemplateMessage(String template) {
        this.templateMessage = template;
    }

    public int getTextTemplateId() {
        return textTemplateId;
    }

    public void setTextTemplateId(int textTemplateId) {
        this.textTemplateId = textTemplateId;
    }

    @NonNull
    @Override
    public String toString() {
        return "text template: " + templateMessage;
    }
}
