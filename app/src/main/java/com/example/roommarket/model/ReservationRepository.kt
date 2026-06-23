package com.example.roommarket.model

object ReservationRepository {

    private val list: MutableList<RoomItem> = mutableListOf()

    fun add(room: RoomItem) {
        list.add(room)
    }

    fun getAll(): List<RoomItem> = list.toList()
}
