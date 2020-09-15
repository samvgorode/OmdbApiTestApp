package com.example.omdbapitestapp.system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.omdbapitestapp.R
import com.example.omdbapitestapp.presentation.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }
}
