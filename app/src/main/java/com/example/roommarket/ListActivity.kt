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

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.list_title)

        val adapter = RoomAdapter { room ->
            val intent = Intent(this, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_ROOM, room)
            startActivity(intent)
        }
        binding.rvRooms.layoutManager = LinearLayoutManager(this)
        binding.rvRooms.adapter = adapter
        adapter.submitList(RoomRepository.load(this))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("RoomMarket", "onQueryTextChange $newText")
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("RoomMarket", "onQueryTextSubmit $query")
                return true
            }
        })
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
        R.id.action_share -> {
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
