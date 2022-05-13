package com.veygard.starwarssage.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.MovieItemBinding
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.util.getPoster

class MoviesListAdapter(private val moviesList: List<Movie>, private val movieClick: MovieClickInterface?, private val context: Context) :
    RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding, movieClick, context)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Movie = moviesList[position]

    override fun getItemCount(): Int = moviesList.size

    class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val movieClick: MovieClickInterface?,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {



        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {

            binding.root.setOnClickListener(this)
            binding.movieItemTitle.text = movie.title
            binding.movieItemDirector.text = context.getString(R.string.movie_item_director,movie.director)
            binding.movieItemEpisode.text = context.getString(R.string.movie_item_episode, movie.episode_id)
            binding.movieItemProducer.text = context.getString(R.string.movie_item_producer, movie.producer)
            binding.movieItemRelease.text = context.getString(R.string.movie_item_release, movie.release_date)
            binding.movieItemText.text = movie.opening_crawl?.replace("\n", "")?.replace("\r", " ")


            getPoster(movie.episode_id)?.let { binding.movieItemPoster.setImageResource(it) }
        }


        override fun onClick(p0: View?) {
        }
    }
}