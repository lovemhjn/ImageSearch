package com.app.imagesearch.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.imagesearch.data.local.db.entity.CommentsEntity

@Dao
interface CommentsDao {

    @Insert
    fun insertComment(comment:CommentsEntity)

    @Query("SELECT comment FROM  CommentsEntity where imageId=:id")
    fun getComments(id: String): List<String>

}