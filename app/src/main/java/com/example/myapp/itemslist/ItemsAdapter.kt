package com.example.myapp.itemslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import java.util.*

class ItemsAdapter (
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
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_card, parent, false)
        val holder = ItemViewHolder(view)

        holder.itemIsFav.setOnClickListener{
            val position = holder.adapterPosition
            items[position].favourited = !items[position].favourited
            notifyItemChanged(position)

        }
      return holder
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

    fun filterList(filterllist: MutableList<ItemCard>) {
        // below line is to add our filtered
        // list in our course array list.
        items = filterllist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
}