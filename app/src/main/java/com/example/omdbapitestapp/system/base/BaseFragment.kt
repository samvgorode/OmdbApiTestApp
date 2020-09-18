package com.example.omdbapitestapp.system.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.omdbapitestapp.system.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected val mainActivity: MainActivity? by lazy {
        requireActivity() as? MainActivity
    }

    var observeVmJob: Job? = null

    override fun onStart() {
        super.onStart()
        observeVmJob = lifecycleScope.launch {
            observeVMState()
        }
    }

    override fun onStop() {
        observeVmJob?.cancel(
            CancellationException(
                "Fragment ${this::class.java.simpleName} stopped"
            )
        )
        super.onStop()
    }

    abstract suspend fun observeVMState()
}
