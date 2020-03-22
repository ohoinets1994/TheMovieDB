package com.example.themoviewdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviewdb.R
import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import kotlinx.android.synthetic.main.layout_genres_list.view.*

class GenresAdapter(private val context: Context) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    var genreWithMovies: List<Pair<Genre, List<Movie>>> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_genres_list, parent, false))

    override fun getItemCount(): Int = genreWithMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moviesAdapter = MoviesAdapter(genreWithMovies[position].second)
        holder.apply {
            bindGenre(genreWithMovies[position].first)
            itemView.rvMovieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rvMovieList.adapter = moviesAdapter
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindGenre(genre: Genre) {
            itemView.apply {
                tvName.text = genre.name
            }
        }
    }
}