package com.example.demexamlastversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.demexamlastversion.databinding.RecyclerViewItemBinding

class WantedAdapter (val listner: Listner) : RecyclerView.Adapter<WantedAdapter.WantedViewHolder>() {

    class WantedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerViewItemBinding.bind(itemView)
        fun bind(wantedModel: WantedModel, listner: Listner) = with(binding) {
            nicknames.text = wantedModel.nicknames
            description.text = wantedModel.description
            itemView.setOnClickListener {
                listner.onClickItem(wantedModel)
            }
        }
    }

    interface Listner {
        fun onClickItem(wantedModel: WantedModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantedViewHolder {
        return WantedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: WantedViewHolder, position: Int) {
        holder.bind(Global.wantedList[position], listner)
    }

    override fun getItemCount() = Global.wantedList.size
}