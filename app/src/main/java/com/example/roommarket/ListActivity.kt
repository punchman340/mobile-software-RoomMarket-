package com.example.roommarket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roommarket.adapter.RoomAdapter
import com.example.roommarket.databinding.ActivityListBinding
import com.example.roommarket.model.RoomRepository
import androidx.appcompat.widget.SearchView

// 숙소 목록 표시 화면
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰바인딩
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //툴바를 액션바로 설정, 뒤로가기 버튼도 활성화
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.list_title)
        //숙소 카드를 선택 시 상세 화면으로
        val adapter = RoomAdapter { room ->
            val intent = Intent(this, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_ROOM, room)
            startActivity(intent)
        }
        // RecyclerView에 LinearLayoutManager랑 어댑터 연결
        binding.rvRooms.layoutManager = LinearLayoutManager(this)
        binding.rvRooms.adapter = adapter
        adapter.submitList(RoomRepository.load(this))
    }
    // 옵션 메뉴 inflate 및 서치뷰 연동
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val menuItem = menu.findItem(R.id.action_search)
        // searchView
        val searchView = menuItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // 검색어 변경 시
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("RoomMarket", "onQueryTextChange $newText")
                return true
            }
            // 검색어 제출 시
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("RoomMarket", "onQueryTextSubmit $query")
                return true
            }
        })
        return true
    }
    // 옵션 메뉴 항목 클릭 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            // 시스템 뒤로가기
            onBackPressedDispatcher.onBackPressed()
            true
        }
        R.id.action_home -> {
            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            true
        }
        R.id.action_share -> {
            // 앱 공유 intent 실행
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            }
            startActivity(Intent.createChooser(send, getString(R.string.share_chooser_title)))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
