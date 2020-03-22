package com.example.themoviewdb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviewdb.R
import com.example.themoviewdb.adapters.GenresAdapter
import com.example.themoviewdb.extensions.observe
import com.example.themoviewdb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MovieViewModel>()

    private val adapter = GenresAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGenreList.layoutManager = LinearLayoutManager(this)
        rvGenreList.adapter = adapter

        viewModel.apply {
            evGenresList.onRetryClicked = { fetchData() }

            observe(genreWithMovies) {
                adapter.genreWithMovies = it
            }
            observe(error) {
                it?.let { error ->
                    rvGenreList.isVisible = false
                    evGenresList.setError(error)
                } ?: run {
                    rvGenreList.isVisible = true
                    evGenresList.hideError()
                }
            }
            fetchData()
        }
    }
}