package com.example.roommarket.model

//예약 숙소 목록 관리용 저장소
object ReservationRepository {

    private val list: MutableList<RoomItem> = mutableListOf()   //예약 목록
    //예약 목록에 숙소 추가
    fun add(room: RoomItem) {
        list.add(room)
    }
    //현재 예약 목록 반환 (읽기 전용)
    fun getAll(): List<RoomItem> = list.toList()
}
