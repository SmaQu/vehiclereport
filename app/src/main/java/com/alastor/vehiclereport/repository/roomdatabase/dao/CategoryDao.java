package com.alastor.vehiclereport.repository.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    Observable<List<Category>> getCategories();

    @Insert
    void insertAll(Category... category);

    @Query("SELECT COUNT(id) FROM Report WHERE category_id =:categoryId")
    Observable<Integer> getCategoryItemCount(String categoryId);

    @Query("SELECT execution_timestamp FROM Report WHERE category_id =:categoryId")
    Observable<Long> getCategoryItemExecutionTimestamp(String categoryId);
}
