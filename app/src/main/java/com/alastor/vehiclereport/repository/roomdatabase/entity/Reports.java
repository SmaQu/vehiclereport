package com.alastor.vehiclereport.repository.roomdatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id"))
public class Reports {

    @PrimaryKey
    @NonNull
    private long id;

    private String title;

    private String description;

    @ColumnInfo(name = "create_timestamp")
    private long createTimestamp;

    @ColumnInfo(name = "edit_timestamp")
    private long editTimestamp;

    private float cost;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getEditTimestamp() {
        return editTimestamp;
    }

    public void setEditTimestamp(long editTimestamp) {
        this.editTimestamp = editTimestamp;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
