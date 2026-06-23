package com.example.roommarket.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

//숙소 데이터 모델
@Parcelize
data class RoomItem(
    val id: String,                     //숙소 식별용 고유 ID
    val name: String,                   //숙소 이름
    val price: Int,                     // 1박 요금
    val description: String,            // 숙소 상세 설명
    val location: String,               // 숙소 위치
    @DrawableRes val imageResId: Int    //drawable 이미지 리소스 ID
) : Parcelable
