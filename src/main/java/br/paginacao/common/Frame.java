package br.paginacao.common;

public class Frame {
    private String pageId;
    private boolean isReferenced;
    private boolean isModified;
    private int duration;

    public Frame(String pageId) {
        this.pageId = pageId;
        this.isReferenced = false;
        this.isModified = false;
        this.duration = 0;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public boolean isReferenced() {
        return isReferenced;
    }

    public void setReferenced(boolean isReferenced) {
        this.isReferenced = isReferenced;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
