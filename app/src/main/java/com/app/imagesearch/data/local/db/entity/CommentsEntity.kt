package com.app.imagesearch.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val imageId: String,
    val comment: String
) {
    constructor(
         imageId: String,
         comment: String
    ):this(null,imageId,comment)
}