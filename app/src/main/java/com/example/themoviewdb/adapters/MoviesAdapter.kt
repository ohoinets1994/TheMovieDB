package com.example.themoviewdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviewdb.R
import com.example.themoviewdb.extensions.load
import com.example.themoviewdb.model.Movie
import kotlinx.android.synthetic.main.layout_movie.view.*

class MoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie, parent, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindMovie(movies[position])

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindMovie(movie: Movie) {
            itemView.apply {
                tvMovieTitle.text = movie.title
                movie.backdrop_path?.let { imgMoviePoster.load(it) }
                movie.poster_path?.let { imgMiniMoviePoster.load(it) }
            }
        }

    }
}