package com.alphitardian.onboardingproject.data.user.data_source.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews() : LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsEntity)
}