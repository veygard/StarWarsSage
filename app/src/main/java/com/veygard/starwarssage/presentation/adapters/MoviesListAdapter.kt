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
import com.veygard.starwarssage.util.toFullString
import com.veygard.starwarssage.util.toLocalDate

class MoviesListAdapter(private var moviesList: List<Movie>, private val movieClick: MovieClickInterface?, private val context: Context) :
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

        private var movie:Movie? = null

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {

            this.movie= movie

            binding.apply {

                root.setOnClickListener(this@MovieViewHolder)
                movieItemTitle.text = movie.title
                movieItemDirector.text = context.getString(R.string.movie_item_director,movie.director)
                movieItemEpisode.text = context.getString(R.string.movie_item_episode, movie.episode_id)
                movieItemProducer.text = context.getString(R.string.movie_item_producer, movie.producer)
                movieItemRelease.text = context.getString(R.string.movie_item_release, movie.release_date?.toLocalDate()?.toFullString())
                movieItemText.text = movie.opening_crawl?.replace("\n", "")?.replace("\r", " ")

                getPoster(movie.episode_id)?.let {
                    movieItemPoster.setImageResource(it)
                    movieImageFull.setImageResource(it)
                }
                movieItemPoster.setOnClickListener {
                    movieImageFull.visibility= View.VISIBLE
                    movieLinearContainer.visibility = View.INVISIBLE
                    movieImageFull.positionAnimator.enter(movieItemPoster, true)
                }
                movieImageFull.setOnClickListener {
                    movieImageFull.positionAnimator.exit(true)
                    movieImageFull.visibility= View.GONE
                    movieLinearContainer.visibility = View.VISIBLE
                }
            }

        }


        override fun onClick(p0: View?) {
            movie?.url?.let { movieClick?.onMovieClick(it) }
        }
    }
}