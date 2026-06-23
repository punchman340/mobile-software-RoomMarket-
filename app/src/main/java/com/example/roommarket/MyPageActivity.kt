package com.example.roommarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.roommarket.databinding.ActivityMyPageBinding

//마이페이지 화면, 안에는 최근 본 숙소랑 예약 내역 버튼 있음
class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ㅂ ㅂㅇㄷ
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //툴바를 액션바로 설정 하고 뒤로 가기 버튼도 활성화
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.mypage_title)
        // 예약 내역 버튼 클릭시 예약 목록으로
        binding.btnViewReservations.setOnClickListener {
            startActivity(Intent(this, ReservationListActivity::class.java))
        }
    }
    // 화면 다시 들어갈 때마다 최근 본 숙소 갱신
    override fun onResume() {
        super.onResume()
        showLastViewed()
    }
    // 마지막으로 본 숙소 이름 표시
    private fun showLastViewed() {
        val prefs = getSharedPreferences(
            getString(R.string.prefs_name),
            Context.MODE_PRIVATE
        )
        val saved = prefs.getString(getString(R.string.prefs_key_last_viewed), null)
        // 마지막으로 본 숙소 없으면 문구 표시
        binding.tvLastViewed.text = saved ?: getString(R.string.mypage_last_viewed_none)
    }
    // 툴바 옵션 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }
    // 옵션 메뉴 클릭 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            // 뒤로 가기 버튼 처리
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
        else -> super.onOptionsItemSelected(item)
    }
}
