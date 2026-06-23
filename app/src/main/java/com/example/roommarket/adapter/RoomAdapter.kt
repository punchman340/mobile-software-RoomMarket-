package com.example.roommarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roommarket.R
import com.example.roommarket.databinding.ItemRoomBinding
import com.example.roommarket.model.RoomItem
import java.text.NumberFormat
import java.util.Locale

// 숙소 목록 RecyclerView에 표시하도록 하는 어댑터
class RoomAdapter(
    private val onClick: (RoomItem) -> Unit         //카드 클릭할 때마다 실행
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items: MutableList<RoomItem> = mutableListOf()
    // 새 목록으로 바꾸고 화면 갱신
    fun submitList(newItems: List<RoomItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        // 아이템 레이아웃
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding, onClick)
    }
    // 몇 번째 카드에 어떤 데이터 넣을지
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(items[position])
    }
    // 목록에 아이템 몇 개인지 확인
    override fun getItemCount(): Int = items.size
    // 각 숙소 카드 뷰홀더
    class RoomViewHolder(
        private val binding: ItemRoomBinding,
        private val onClick: (RoomItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        // 숙소 데이터를 각 뷰에다가 바인딩
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
