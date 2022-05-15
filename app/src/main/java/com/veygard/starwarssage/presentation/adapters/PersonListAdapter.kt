package com.veygard.starwarssage.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.PersonItemBinding
import com.veygard.starwarssage.domain.model.Person

class PersonListAdapter(private var personList: List<Person>, private val personClick: PersonClickInterface?, private val context: Context) :
    RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PersonItemBinding.inflate(layoutInflater, parent, false)
        return PersonViewHolder(binding, personClick, context)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Person = personList[position]

    override fun getItemCount(): Int = personList.size

    class PersonViewHolder(
        private val binding: PersonItemBinding,
        private val personClick: PersonClickInterface?,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var person:Person? = null

        @SuppressLint("SetTextI18n")
        fun bind(person: Person) {

            this.person= person

            binding.apply {
                root.setOnClickListener(this@PersonViewHolder)
                personItemTitle.text= person.name
                personItemSex.text = context.getString(R.string.person_item_sex, person.gender)
                personItemBornDate.text = context.getString(R.string.person_item_born_date, person.birth_year)
                personAvatar.load(person.avatarUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_error)
                    error(R.drawable.ic_movie_error)
                }
            }

        }


        override fun onClick(p0: View?) {
            person?.homeworld?.let { personClick?.onPersonClick(it) }
        }
    }
}