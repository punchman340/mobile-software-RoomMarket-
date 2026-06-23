package com.example.roommarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.roommarket.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.mypage_title)

        binding.btnViewReservations.setOnClickListener {
            startActivity(Intent(this, ReservationListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        showLastViewed()
    }

    private fun showLastViewed() {
        val prefs = getSharedPreferences(
            getString(R.string.prefs_name),
            Context.MODE_PRIVATE
        )
        val saved = prefs.getString(getString(R.string.prefs_key_last_viewed), null)
        binding.tvLastViewed.text = saved ?: getString(R.string.mypage_last_viewed_none)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

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
