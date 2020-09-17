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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MoviesListFragment: Fragment(R.layout.movies_list_fragment) {

    private val viewModel: MoviesListViewModel by viewModel()
    private var binding: MoviesListFragmentBinding? = null

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
            progress.isVisible = true
            goToSearch.setOnClickListener {
                (requireActivity() as? MainActivity)?.navigateSafe(R.id.action_list_to_start_search)
            }
        }
    }

    @FlowPreview
    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.observeState().collect { state ->
                val list = state?.list ?: return@collect
                binding?.run {
                    progress.isVisible = false
                    if (list.isEmpty()) {
                        moviesList.isVisible = false
                        noResults.isVisible = true
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
