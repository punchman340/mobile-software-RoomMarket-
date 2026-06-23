package com.example.roommarket.model

import android.content.Context
import com.example.roommarket.R

object RoomRepository {

    fun load(context: Context): List<RoomItem> = listOf(
        RoomItem(
            id = "room_01",
            name = context.getString(R.string.room_01_name),
            price = 128_000,
            description = context.getString(R.string.room_01_desc),
            imageResId = R.drawable.room_01
        ),
        RoomItem(
            id = "room_02",
            name = context.getString(R.string.room_02_name),
            price = 156_000,
            description = context.getString(R.string.room_02_desc),
            imageResId = R.drawable.room_02
        ),
        RoomItem(
            id = "room_03",
            name = context.getString(R.string.room_03_name),
            price = 212_000,
            description = context.getString(R.string.room_03_desc),
            imageResId = R.drawable.room_03
        ),
        RoomItem(
            id = "room_04",
            name = context.getString(R.string.room_04_name),
            price = 178_000,
            description = context.getString(R.string.room_04_desc),
            imageResId = R.drawable.room_04
        ),
        RoomItem(
            id = "room_05",
            name = context.getString(R.string.room_05_name),
            price = 198_000,
            description = context.getString(R.string.room_05_desc),
            imageResId = R.drawable.room_05
        ),
        RoomItem(
            id = "room_06",
            name = context.getString(R.string.room_06_name),
            price = 285_000,
            description = context.getString(R.string.room_06_desc),
            imageResId = R.drawable.room_06
        )
    )
}
