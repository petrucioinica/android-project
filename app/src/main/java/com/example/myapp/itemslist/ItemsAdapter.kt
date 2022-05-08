package com.example.myapp.itemslist

import android.content.ClipData
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class ItemsAdapter(
    private var items: MutableList<ItemCard>
): RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemText: TextView = itemView.findViewById(R.id.itemText)
        var itemIsFav: ImageView = itemView.findViewById(R.id.itemIsFav)
        var image: ImageView = itemView.findViewById((R.id.itemImage))
        init{
            itemIsFav.setOnClickListener{
            }
        }
    }

    var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      return ItemViewHolder(
          LayoutInflater.from(parent.context).inflate(
              R.layout.item_card,
              parent,
              false
          )
      )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        print(currentItem)
        holder.itemText.text = currentItem.title
        if(currentItem.favourited){
            holder.itemIsFav.setBackgroundResource(R.drawable.favourite_checked)
        }
        else{
            holder.itemIsFav.setBackgroundResource(R.drawable.favourite_unchecked)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(newItem: ItemCard){
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }
}