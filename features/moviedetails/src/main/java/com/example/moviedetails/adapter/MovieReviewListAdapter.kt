package com.example.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.entity.moviedetails.Review
import com.example.moviedetails.databinding.MovieReviewItemBinding

class MovieReviewListAdapter(
    private val clickListener: MovieReviewClick
) : BaseAdapter<Review, MovieReviewListAdapter.MovieReviewViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.author == newItem.author }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
        return MovieReviewViewHolder(
            MovieReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieReviewClick(val clickListener: (model: Review) -> Unit) {
        fun onClick(model: Review) = clickListener(model)
    }

    class MovieReviewViewHolder(private val binding: MovieReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Review) {
            binding.apply {
                reviewerName.text = model.title
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
