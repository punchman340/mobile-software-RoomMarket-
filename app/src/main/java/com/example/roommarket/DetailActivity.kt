package com.example.roommarket

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.roommarket.databinding.ActivityDetailBinding
import com.example.roommarket.model.ReservationRepository
import com.example.roommarket.model.RoomItem
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.Locale

// 선택한 숙소 상세 정보 표시 및 예약을 처리하는 화면
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var room: RoomItem? = null // Intent로 전달받은 숙소

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰바인딩
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 툴바도 액션바로, 뒤로가기 버튼도 활성화
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detail_title)
        // Intent로 전달되어진 숙소 데이터
        room = readRoomExtra()
        val current = room
        if (current != null) {
            bindRoom(current)       //숙소 정보 표시
            saveLastViewed(current.name)    // 마지막으로 본 숙소 기억
        }
        //에약하기 버튼을 누를 시 AlertDialog (예약이 완료되었습니다)
        binding.btnReserve.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.reserve_dialog_title))
                .setMessage(getString(R.string.reserve_dialog_message))
                .setPositiveButton(getString(R.string.reserve_confirm)) { _, _ ->
                    room?.let {
                        ReservationRepository.add(it)   // 예약 목록에 넣기
                        Snackbar.make(
                            binding.root,
                            getString(R.string.reserve_done),
                            Snackbar.LENGTH_LONG
                        ).show()    //스낵바 표시
                    }
                }
                .setNegativeButton(getString(R.string.reserve_cancel), null)
                .show()
        }
    }
    // 숙소 데이터 뷰들에 바인딩하는 함수
    private fun bindRoom(item: RoomItem) {
        binding.imgRoom.setImageResource(item.imageResId)
        binding.tvName.text = item.name
        val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
        binding.tvPrice.text = getString(R.string.price_per_night, formatted)
        binding.tvLocation.text = getString(R.string.label_location) + ": " + item.location
        binding.tvDescription.text = item.description
    }
    //마지막으로 본 숙소 저장하는 함수
    private fun saveLastViewed(name: String) {
        val prefs = getSharedPreferences(
            getString(R.string.prefs_name),
            Context.MODE_PRIVATE
        )
        prefs.edit().putString(getString(R.string.prefs_key_last_viewed), name).apply()
    }
    // Intent에서 RoomItem 읽는 함수
    @Suppress("DEPRECATION")
    private fun readRoomExtra(): RoomItem? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ROOM, RoomItem::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_ROOM)
        }
    }
    // 옵션 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }
    // 옵션 메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_home -> {
            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            true
        }
        R.id.action_share -> {
            // 공유하기
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            }
            startActivity(Intent.createChooser(send, getString(R.string.share_chooser_title)))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
    //툴바 뒤로가기 버튼 처리
    companion object {
        const val EXTRA_ROOM = "extra_room"
    }
}
