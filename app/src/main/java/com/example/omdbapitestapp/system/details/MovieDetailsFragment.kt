package com.example.omdbapitestapp.system.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.load
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.databinding.MovieDetailsFragmentBinding
import com.example.omdbapitestapp.presentation.details.MovieDetailsViewModel
import com.example.omdbapitestapp.system.base.BaseFragment
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment(R.layout.movie_details_fragment) {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var binding: MovieDetailsFragmentBinding? = null
    private var imdbId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieDetailsFragmentBinding.bind(view)
        binding?.detailsLayout?.let { details ->
            details.watchLaterWrapper.setOnClickListener {
                imdbId?.let { viewModel.onWatchLater(it, details.watchLaterCheck.isChecked.not()) }
            }
            details.watchedWrapper.setOnClickListener {
                imdbId?.let { viewModel.onWatched(it, details.watchedCheck.isChecked.not()) }
            }
        }
        binding?.goToList?.setOnClickListener {
            mainActivity?.navigateSafe(R.id.action_details_to_list)
        }
    }

    override suspend fun observeVMState() {
        viewModel.observeState().collect { state ->
            (state == null).let {
                binding?.progress?.isVisible = it
                if (it) return@collect
            }
            val movie = state?.movie
            val error = state?.errorMessage
            binding?.run {
                if (movie == null || error != null) {
                    contentLayout.isVisible = false
                    errorText.isVisible = true
                    errorText.text = error ?: getString(R.string.no_results)
                } else {
                    imdbId = movie.imdbId
                    contentLayout.isVisible = true
                    errorText.isVisible = false
                    movieCover.load(movie.poster) {
                        placeholder(R.drawable.ic_no_preview)
                    }
                    detailsLayout.run {
                        titleText.text = movie.title
                        yearText.text = movie.year
                        typeText.text = movie.type
                        watchLaterCheck.isChecked = movie.watchLater
                        watchedCheck.isChecked = movie.watched
                    }
                    detailsMaxLayout.run {
                        imdbRatingText.text = movie.imdbRating
                        imdbVotesText.text = movie.imdbVotes
                        releasedText.text = movie.released
                        countryText.text = movie.country
                        languageText.text = movie.language
                        directorText.text = movie.director
                        productionText.text = movie.production
                        genreText.text = movie.genre
                        runtimeText.text = movie.runtime
                        actorsText.text = movie.actors
                        plotText.text = movie.plot
                    }
                }
            }
        }
    }
}
