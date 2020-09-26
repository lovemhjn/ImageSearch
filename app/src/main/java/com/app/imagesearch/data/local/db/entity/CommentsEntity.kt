package com.app.imagesearch.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val imageId: Int,
    val comment: String
) {
    constructor(
         imageId: Int,
         comment: String
    ):this(null,imageId,comment)
}