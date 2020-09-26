package com.app.imagesearch.data.remote.repository

import com.app.imagesearch.data.local.db.dao.CommentsDao
import com.app.imagesearch.data.local.db.entity.CommentsEntity
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val commentsDao: CommentsDao)  {

    fun getComments(id: String): List<String>{
        return commentsDao.getComments(id)
    }

    fun addComment(comment: CommentsEntity){
        commentsDao.insertComment(comment)
    }
}