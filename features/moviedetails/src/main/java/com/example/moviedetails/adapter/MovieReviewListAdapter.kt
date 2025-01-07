package com.example.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.entity.moviedetails.Poster
import com.example.moviedetails.databinding.MovieReviewItemBinding
import com.example.uikit.extensions.loadImageFromGLideRounded

class MovieReviewListAdapter(
    private val clickListener: MovieReviewClick
) : BaseAdapter<Poster, MovieReviewListAdapter.MovieReviewViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.imageUrl == newItem.imageUrl }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
        return MovieReviewViewHolder(
            MovieReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieReviewClick(val clickListener: (model: Poster) -> Unit) {
        fun onClick(model: Poster) = clickListener(model)
    }

    class MovieReviewViewHolder(private val binding: MovieReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Poster) {
            binding.apply {
                poster.loadImageFromGLideRounded(model.previewUrl.toString(), 25, null)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
