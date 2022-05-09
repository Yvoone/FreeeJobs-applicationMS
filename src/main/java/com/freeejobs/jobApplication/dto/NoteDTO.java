package com.freeejobs.jobApplication.dto;

import java.util.Map;

public class NoteDTO {
    private String subject;
    private String content;
    private Map<String, String> data;
    

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getData() {
        return data;
    }
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}