package com.example.roommarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roommarket.adapter.RoomAdapter
import com.example.roommarket.databinding.ActivityReservationListBinding
import com.example.roommarket.model.ReservationRepository

// 예약 내역 화면
class ReservationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationListBinding
    private lateinit var adapter: RoomAdapter   //예약 목록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ㅂ ㅂㅇㄷ
        binding = ActivityReservationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //툴바랑 뒤로 가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.reservation_title)
        //예약 목록은 클릭 없이 그냥 보기만.
        adapter = RoomAdapter { /* no-op: read-only history */ }
        binding.rvReservations.layoutManager = LinearLayoutManager(this)
        binding.rvReservations.adapter = adapter
    }
    // 화면 재진입할 때마다 예약 목록 갱신되도록
    override fun onResume() {
        super.onResume()
        val items = ReservationRepository.getAll()
        adapter.submitList(items)
        updateEmptyState(items.isEmpty())   // 목록이 비면 전환
    }
    // 예약 내역 없을 때 문구 표시
    private fun updateEmptyState(isEmpty: Boolean) {
        binding.rvReservations.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
    // 툴바 옵션 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }
    //옵션 메뉴 항목 클릭 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            true
        }
        R.id.action_home -> {
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
