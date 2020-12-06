package fr.airweb.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.airweb.news.databinding.ItemNewsBinding
import fr.airweb.news.repository.model.NewModel

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class NewsAdapter(clickListener: ItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: ArrayList<NewModel> = arrayListOf()
    private var itemClickListener: ItemClickListener = clickListener

    fun setNews(listNews: ArrayList<NewModel>) {
        newsList.clear()
        newsList.addAll(listNews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val new = newsList[position]
        holder.bind(new)
        holder.binding.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    inner class NewsViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewModel) {
            binding.newModel = item
            binding.executePendingBindings()
            Picasso.get()
                .load(item.picture)
                .into(binding.imageNew)
        }

    }


    interface ItemClickListener {
        fun onItemClick(newModel: NewModel)
    }


}