package com.app.imagesearch.ui.comments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.imagesearch.data.local.db.entity.CommentsEntity
import com.app.imagesearch.data.remote.repository.CommentsRepository

class CommentsViewModel @ViewModelInject constructor(private val repository: CommentsRepository): ViewModel() {

    private val _commentsList = MutableLiveData<List<String>>()

    val commentList:LiveData<List<String>>
    get() = _commentsList

    fun getComments(id:String){
        _commentsList.postValue(repository.getComments(id))
    }

    fun addComment(comment:CommentsEntity){
        repository.addComment(comment)
    }

}