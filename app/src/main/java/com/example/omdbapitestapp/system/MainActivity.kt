package com.example.omdbapitestapp.system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.presentation.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
