package com.example.omdbapitestapp.system

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.omdbapitestapp.R

class MainActivity : AppCompatActivity() {

    private val inputManager: InputMethodManager? by lazy {
        getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    fun navigateSafe(@IdRes resId: Int) {
        val navController = findNavController(R.id.nav_host_fragment)
        val currentDestination = navController.currentDestination
        val graph = navController.graph
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
        if (action != null && currentDestination?.id != action.destinationId) {
            navController.navigate(resId)
        }
    }

    fun showKeyboard() {
        window.decorView.postDelayed({
            inputManager?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }, 150)
    }

    fun hideKeyboard() {
        window.decorView.postDelayed({
            inputManager?.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        }, 100)
    }
}
