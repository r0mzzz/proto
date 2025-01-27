package com.example.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.entity.home.MovieModel
import com.example.home.databinding.MovieItemBinding
import com.example.uikit.extensions.loadImageFromGLideRounded

class MovieListAdapter(
    private val clickListener: MovieItemClick
) : BaseAdapter<MovieModel, MovieListAdapter.MovieListViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.nameOriginal == newItem.nameOriginal }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieItemClick(val clickListener: (model: MovieModel) -> Unit) {
        fun onClick(model: MovieModel) = clickListener(model)
    }

    class MovieListViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieModel) {
            binding.apply {
                model.posterUrlPreview?.let { binding.poster.loadImageFromGLideRounded(it, 24) }
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
