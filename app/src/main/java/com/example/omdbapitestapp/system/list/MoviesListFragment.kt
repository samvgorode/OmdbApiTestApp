package com.example.omdbapitestapp.system.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.databinding.MoviesListFragmentBinding
import com.example.omdbapitestapp.presentation.list.MoviesListViewModel
import com.example.omdbapitestapp.system.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MoviesListFragment : Fragment(R.layout.movies_list_fragment) {

    private val viewModel: MoviesListViewModel by viewModel()
    private var binding: MoviesListFragmentBinding? = null
    private val mainActivity: MainActivity? by lazy {
        requireActivity() as? MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesListFragmentBinding.bind(view)
        binding?.run {
            moviesList.run {
                layoutManager = object : LinearLayoutManager(requireContext()) {
                    override fun supportsPredictiveItemAnimations(): Boolean = false
                }
                adapter = MoviesListAdapter(viewModel)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            goToSearch.setOnClickListener {
                mainActivity?.navigateSafe(R.id.action_list_to_start_search)
            }
        }
    }

    @FlowPreview
    override fun onStart() {
        super.onStart()
        binding?.progress?.isVisible = true
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.observeState().collect { state ->
                state?.run {
                    if (goToMovieDetails) {
                        goToMovieDetails()
                        return@collect
                    }
                    binding?.run {
                        progress.isVisible = false
                        if(list.isNullOrEmpty() || errorMessage != null) {
                            moviesList.isVisible = false
                            noResults.isVisible = true
                            noResults.text = errorMessage?:getString(R.string.no_results)
                        } else {
                            moviesList.isVisible = true
                            noResults.isVisible = false
                            (moviesList.adapter as? MoviesListAdapter)?.submitList(list)
                        }
                    }
                }
            }
        }
    }

    private fun goToMovieDetails() {
        mainActivity?.navigateSafe(R.id.action_list_to_details)
    }
}
