package com.sunitawebapp.locationonmap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunitawebapp.locationonmap.databinding.ItemDetailsBinding

class TopicAdapter(var topiclist : ArrayList<String>) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {
    lateinit var onClickRecyclerView:  OnClickRecyclerView
    class TopicViewHolder(var binding: ItemDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
       fun setBinding(list : String){
           binding.tvItem.text=list
       }
    }

    fun setOnItemClickListener(onClickRecyclerView: OnClickRecyclerView) {
        this.onClickRecyclerView=onClickRecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
      return TopicViewHolder(ItemDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
       holder.setBinding(topiclist.get(position))
        holder.itemView.setOnClickListener {v->
            onClickRecyclerView.setOnClickRecyclerView(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
      return topiclist.size
    }

    interface OnClickRecyclerView{
        fun setOnClickRecyclerView(position: Int)
    }






}
