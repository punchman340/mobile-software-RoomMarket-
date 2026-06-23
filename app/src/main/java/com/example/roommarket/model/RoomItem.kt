package com.example.roommarket.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomItem(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    @DrawableRes val imageResId: Int
) : Parcelable
