package com.example.roommarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.roommarket.databinding.ActivityMainBinding

// 메인 화면, navigationDrawer랑 Toolbar 제공함
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle    //ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 툴바를 액션바로 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        title = getString(R.string.app_name)
        //NavigationDrawer 토클 설정
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()    //토글 상태 sync
        // drawer 메뉴 클릭 처리
        binding.navView.setNavigationItemSelectedListener { item ->
            val target = when (item.itemId) {
                R.id.nav_room_list -> Intent(this, ListActivity::class.java)
                R.id.nav_reservation_list -> Intent(this, ReservationListActivity::class.java)
                R.id.nav_mypage -> Intent(this, MyPageActivity::class.java)
                else -> null
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            target?.let { startActivity(it) }
            true
        }
        // '숙소 둘러보기'라는 메인화면에 있는 버튼 클릭시 목록 화면으로
        binding.btnGoList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
        //뒤로가기 했을 시에 드로어가 열려있음 닫기
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
    // 툴바 옵션 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    //옵션 메뉴 항목 클릭 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return when (item.itemId) {
            R.id.action_share -> {
                shareApp()  // 공유
                true
            }
            R.id.action_home -> {
                goHome()    // 홈으로
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // 앱 공유 실행
    private fun shareApp() {
        val send = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        }
        startActivity(Intent.createChooser(send, getString(R.string.share_chooser_title)))
    }
    // 홈으로 이동(백스택 초기화)
    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}
