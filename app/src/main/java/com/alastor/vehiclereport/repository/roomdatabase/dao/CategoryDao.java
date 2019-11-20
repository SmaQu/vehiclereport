package com.alastor.vehiclereport.repository.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    Observable<List<Category>> getCategories();

    @Insert
    void insertAll(Category... category);
}
