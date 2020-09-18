package com.example.omdbapitestapp.system.start

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.databinding.StartSearchFragmentBinding
import com.example.omdbapitestapp.presentation.start.StartSearchViewModel
import com.example.omdbapitestapp.system.base.BaseFragment
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class StartSearchFragment : BaseFragment(R.layout.start_search_fragment) {

    private val viewModel: StartSearchViewModel by viewModel()
    private var binding: StartSearchFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StartSearchFragmentBinding.bind(view)
        binding?.run {
            searchInput.requestFocus()
            mainActivity?.showKeyboard()
            search.setOnClickListener { startSearch() }
            searchInput.setOnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    startSearch()
                    true
                } else false
            }
        }
    }

    private fun startSearch() {
        viewModel.saveQuery(binding?.searchInput?.text.toString())
        mainActivity?.navigateSafe(R.id.action_start_search_to_list)
        mainActivity?.hideKeyboard()
    }

    override suspend fun observeVMState() {
        viewModel.observeState().collect {
            it?.query?.let { query ->
                binding?.searchInput?.run {
                    setText(query, TextView.BufferType.EDITABLE)
                    setSelection(this.length())
                }
            }
        }
    }
}
