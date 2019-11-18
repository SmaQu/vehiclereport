package com.alastor.vehiclereport.repository.roomdatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "edit_timestamp")
    private long editTimestamp;

    @ColumnInfo(name = "amount_of_elements")
    private long amountOfElements;

    public Category(String id, long editTimestamp, long amountOfElements) {
        this.id = id;
        this.editTimestamp = editTimestamp;
        this.amountOfElements = amountOfElements;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEditTimestamp() {
        return editTimestamp;
    }

    public void setEditTimestamp(long editTimestamp) {
        this.editTimestamp = editTimestamp;
    }

    public long getAmountOfElements() {
        return amountOfElements;
    }

    public void setAmountOfElements(long amountOfElements) {
        this.amountOfElements = amountOfElements;
    }

    public static Category[] populateData() {
        return new Category[] {
                new Category("BFC", System.currentTimeMillis(), 0),
                new Category("BKS", System.currentTimeMillis(), 0),
                new Category("CWH", System.currentTimeMillis(), 0),
                new Category("CLS", System.currentTimeMillis(), 0),
                new Category("DRS", System.currentTimeMillis(), 0),
                new Category("ESL", System.currentTimeMillis(), 0),
                new Category("EAE", System.currentTimeMillis(), 0),
                new Category("EAA", System.currentTimeMillis(), 0),
                new Category("EXS", System.currentTimeMillis(), 0),
                new Category("FIL", System.currentTimeMillis(), 0),
                new Category("FSP", System.currentTimeMillis(), 0),
                new Category("HAC", System.currentTimeMillis(), 0),
                new Category("IIS", System.currentTimeMillis(), 0),
                new Category("ITS", System.currentTimeMillis(), 0),
                new Category("STS", System.currentTimeMillis(), 0),
                new Category("SAW", System.currentTimeMillis(), 0)
        };
    }
}
