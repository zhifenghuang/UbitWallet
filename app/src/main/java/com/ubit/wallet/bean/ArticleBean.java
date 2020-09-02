package com.ubit.wallet.bean;

import java.io.Serializable;

public class ArticleBean implements Serializable {

    private long aid;
    private String add_time;
    private String synopsis;
    private String synopsis_en;
    private String title;
    private String title_en;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSynopsis_en() {
        return synopsis_en;
    }

    public void setSynopsis_en(String synopsis_en) {
        this.synopsis_en = synopsis_en;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }
}
