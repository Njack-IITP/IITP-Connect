package com.iitp.njack.iitp_connect.data.contest;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContestDao {
    @Query("SELECT * FROM " + Contest.TABLE_NAME)
    LiveData<List<Contest>> getContests();

    @Query("SELECT * FROM " + Contest.TABLE_NAME + " WHERE id=:id")
    LiveData<Contest> getContestById(long id);

    @Insert(onConflict = REPLACE)
    void addContest(Contest contest);

    @Delete
    void deleteContest(Contest contest);

    @Update
    void updateContest(Contest contest);
}
