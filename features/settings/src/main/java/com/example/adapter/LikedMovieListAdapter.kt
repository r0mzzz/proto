package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.database.entity.LikedMovies
import com.example.settings.databinding.MovieItemBinding
import com.example.uikit.extensions.loadImageFromGLideRounded

class LikedMovieListAdapter(
    private val clickListener: MovieItemClick
) : BaseAdapter<LikedMovies, LikedMovieListAdapter.MovieListViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.movieTitle == newItem.movieTitle }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieItemClick(val clickListener: (model: LikedMovies) -> Unit) {
        fun onClick(model: LikedMovies) = clickListener(model)
    }

    class MovieListViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: LikedMovies) {
            binding.apply {
                model.moviePoster.let { binding.poster.loadImageFromGLideRounded(it, 24) }
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
