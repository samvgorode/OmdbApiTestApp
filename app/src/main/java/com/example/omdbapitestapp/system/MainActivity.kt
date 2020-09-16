package com.example.omdbapitestapp.system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.presentation.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
