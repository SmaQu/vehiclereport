package com.alastor.vehiclereport.repository.roomdatabase.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id"))
public class Report {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    private String description;

    @ColumnInfo(name = "execution_timestamp")
    private long executionTimestamp;

    private double cost;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    public Report() {
        this.title = "";
        this.description = "";
        this.executionTimestamp = System.currentTimeMillis();
        this.cost = 0f;
        this.categoryId = "";
    }

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

    public long getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(long executionTimestamp) {
        this.executionTimestamp = executionTimestamp;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
