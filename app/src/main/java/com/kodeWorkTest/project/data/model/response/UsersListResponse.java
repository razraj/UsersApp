package com.kodeWorkTest.project.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class UsersListResponse extends RealmObject {
    @PrimaryKey
    private int page;
    @SerializedName("data")
    private RealmList<UserData> data;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("total")
    private int total;
    @SerializedName("per_page")
    private int per_page;

    public List<UserData> getData() {
        return data;
    }

    public void setData(RealmList<UserData> data) {
        this.data = data;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }



}
