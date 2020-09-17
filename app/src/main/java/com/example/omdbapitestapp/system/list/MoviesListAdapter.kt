package com.example.omdbapitestapp.system.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.databinding.MoviesListItemBinding
import com.example.omdbapitestapp.model.MovieModel
import com.example.omdbapitestapp.presentation.list.MoviesListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MoviesListAdapter(private val viewModel: MoviesListViewModel) :
    ListAdapter<MovieModel, RecyclerView.ViewHolder>(DiscoverSearchEqualityCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        LayoutInflater
            .from(parent.context).run {
                MovieViewHolder(inflate(R.layout.movies_list_item, parent, false))
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MovieViewHolder)?.run {
            val item = getItem(position)
            onBindViewHolder(this, item)
        }
    }

    private fun onBindViewHolder(holder: MovieViewHolder, row: MovieModel) {
        holder.binding.run {
            titleText.text = row.title
            yearText.text = row.year
            typeText.text = row.type
            watchLaterCheck.isChecked = row.watchLater
            watchedCheck.isChecked = row.watched
            posterImage.load(row.posterLink)
            watchLaterCheck.setOnCheckedChangeListener { _, b ->
                viewModel.onWatchLater(row.imdbID, b)
            }
            watchedCheck.setOnCheckedChangeListener { _, b ->
                viewModel.onWatched(row.imdbID, b)
            }
        }
    }

    private class MovieViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val binding = MoviesListItemBinding.bind(containerView)
    }

    private class DiscoverSearchEqualityCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel,
        ): Boolean = oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem == newItem
    }
}
