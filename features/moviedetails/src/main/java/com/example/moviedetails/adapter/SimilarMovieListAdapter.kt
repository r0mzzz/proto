package com.example.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.entity.moviedetails.SimilarMovieModel
import com.example.moviedetails.databinding.MovieItemBinding
import com.example.uikit.extensions.loadImageFromGLideRounded

class SimilarMovieListAdapter(
    private val clickListener: MovieItemClick
) : BaseAdapter<SimilarMovieModel, SimilarMovieListAdapter.MovieListViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.nameRu == newItem.nameRu }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieItemClick(val clickListener: (model: SimilarMovieModel) -> Unit) {
        fun onClick(model: SimilarMovieModel) = clickListener(model)
    }

    class MovieListViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SimilarMovieModel) {
            binding.apply {
                model.posterUrl?.let { binding.poster.loadImageFromGLideRounded(it, 24) }
            }
        }
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
