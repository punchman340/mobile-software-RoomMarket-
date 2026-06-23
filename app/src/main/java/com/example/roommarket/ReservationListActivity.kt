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

class ReservationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationListBinding
    private lateinit var adapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.reservation_title)

        adapter = RoomAdapter { /* no-op: read-only history */ }
        binding.rvReservations.layoutManager = LinearLayoutManager(this)
        binding.rvReservations.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val items = ReservationRepository.getAll()
        adapter.submitList(items)
        updateEmptyState(items.isEmpty())
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.rvReservations.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
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
