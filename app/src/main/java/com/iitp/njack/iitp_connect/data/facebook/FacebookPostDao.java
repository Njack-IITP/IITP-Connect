package com.iitp.njack.iitp_connect.data.facebook;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FacebookPostDao {
    @Query("SELECT * FROM " + FacebookPost.TABLE_NAME)
    LiveData<List<FacebookPost>> getFacebookPosts();

    @Query("SELECT * FROM " + FacebookPost.TABLE_NAME + " WHERE id=:id")
    LiveData<FacebookPost> getFacebookPostById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFacebookPost(FacebookPost facebookPost);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFacebookPosts(List<FacebookPost> facebookPosts);

    @Delete
    void deleteFacebookPost(FacebookPost facebookPost);

    @Query("DELETE FROM " + FacebookPost.TABLE_NAME)
    void deleteAll();

    @Update
    void updateFacebookPost(FacebookPost facebookPost);
}
