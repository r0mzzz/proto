package com.example.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.domain.entity.home.MovieItemModel
import com.example.home.databinding.MovieItemBinding

class MovieListAdapter(private val itemList: List<MovieItemModel>, private val clickListener: MovieItemClick) : BaseAdapter<MovieItemModel, MovieListAdapter.MovieListViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.poster == newItem.poster }) {

    init {
        submitList(itemList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class MovieItemClick(val clickListener: (model: MovieItemModel) -> Unit) {
        fun onClick(model: MovieItemModel) = clickListener(model)
    }

    class MovieListViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieItemModel) {
            binding.apply {
                model.poster?.let { poster.setImageResource(it) }
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
