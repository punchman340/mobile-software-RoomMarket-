package com.example.roommarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roommarket.R
import com.example.roommarket.databinding.ItemRoomBinding
import com.example.roommarket.model.RoomItem
import java.text.NumberFormat
import java.util.Locale

class RoomAdapter(
    private val onClick: (RoomItem) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items: MutableList<RoomItem> = mutableListOf()

    fun submitList(newItems: List<RoomItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RoomViewHolder(
        private val binding: ItemRoomBinding,
        private val onClick: (RoomItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)

        fun bind(item: RoomItem) {
            val ctx = binding.root.context
            binding.imgRoom.setImageResource(item.imageResId)
            binding.tvName.text = item.name
            binding.tvPrice.text = ctx.getString(
                R.string.price_per_night,
                numberFormat.format(item.price)
            )
            binding.tvDescription.text = item.description
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
