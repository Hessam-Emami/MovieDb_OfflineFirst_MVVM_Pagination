package com.emami.moviedb.movie.ui.discover

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.emami.moviedb.R
import com.emami.moviedb.common.base.BasePagingDataAdapter
import com.emami.moviedb.common.util.loadImage
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import kotlinx.android.synthetic.main.item_recycler_movie.view.*
import javax.inject.Inject


class MoviePagingDataAdapter @Inject constructor() :
    BasePagingDataAdapter<MovieEntity>(
        DiffCallback
    ) {

    override fun getLayoutId(): Int {
        return R.layout.item_recycler_movie
    }

    override fun bindView(): (item: MovieEntity, itemView: View, position: Int) -> Unit {
        return { item, itemView, _ ->
            itemView.apply {
                title.text = item.title
                date.text = item.releaseDate
                imageView.loadImage(item.posterLink)
            }
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: MovieEntity,
            newItem: MovieEntity
        ): Boolean =
            oldItem == newItem
    }
}