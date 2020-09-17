package com.example.omdbapitestapp.system.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.databinding.MoviesListFragmentBinding
import com.example.omdbapitestapp.databinding.StartSearchFragmentBinding
import com.example.omdbapitestapp.presentation.list.MoviesListViewModel
import com.example.omdbapitestapp.presentation.start.StartSearchViewModel
import com.example.omdbapitestapp.system.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class StartSearchFragment: Fragment(R.layout.start_search_fragment) {

    private val viewModel: StartSearchViewModel by viewModel()
    private var binding: StartSearchFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StartSearchFragmentBinding.bind(view)
        binding?.run {
            search.setOnClickListener {
                viewModel.saveQuery(searchInput.text.toString())
                (requireActivity() as? MainActivity)?.navigateSafe(R.id.action_start_search_to_list)
            }
        }
    }
}
