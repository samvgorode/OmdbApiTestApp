package com.example.omdbapitestapp.system

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.omdbapitestapp.R

class MainActivity : AppCompatActivity() {

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
}
