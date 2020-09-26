package com.app.imagesearch.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.imagesearch.R
import com.app.imagesearch.constants.AppConstants
import com.app.imagesearch.data.remote.model.ImageSearchResponse
import com.app.imagesearch.ui.comments.CommentsActivity
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

class ImagesRecyclerAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val oldList by lazy { ArrayList<ImageSearchResponse.Data>() }

    @Inject
    lateinit var glideInstance: RequestManager

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageSearchResponse.Data>() {

        override fun areItemsTheSame(
            oldItem: ImageSearchResponse.Data,
            newItem: ImageSearchResponse.Data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ImageSearchResponse.Data,
            newItem: ImageSearchResponse.Data
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ImagesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImagesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ImageSearchResponse.Data>) {
        oldList.clear()
        oldList.addAll(list)
        differ.submitList(list)
    }

    fun addMoreData(moreData: List<ImageSearchResponse.Data>) {
        oldList.addAll(moreData)
        differ.submitList(oldList)
    }

    inner class ImagesViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ImageSearchResponse.Data) = with(itemView) {
            itemView.apply {
                item.images?.let {
                    glideInstance.load(it[0].link).placeholder(R.drawable.placeholder).into(img)
                }
                if (item.images == null) {
                    llParent.visibility = View.GONE
                } else {
                    llParent.visibility = View.VISIBLE
                }

                setOnClickListener {
                    context.startActivity(Intent(
                        context,
                        CommentsActivity::class.java
                    ).apply { putExtra(AppConstants.DATA, item) })
                }

            }
        }
    }
}