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

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var room: RoomItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detail_title)

        room = readRoomExtra()
        val current = room
        if (current != null) {
            bindRoom(current)
            saveLastViewed(current.name)
        }

        binding.btnReserve.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.reserve_dialog_title))
                .setMessage(getString(R.string.reserve_dialog_message))
                .setPositiveButton(getString(R.string.reserve_confirm)) { _, _ ->
                    room?.let {
                        ReservationRepository.add(it)
                        Snackbar.make(
                            binding.root,
                            getString(R.string.reserve_done),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                .setNegativeButton(getString(R.string.reserve_cancel), null)
                .show()
        }
    }

    private fun bindRoom(item: RoomItem) {
        binding.imgRoom.setImageResource(item.imageResId)
        binding.tvName.text = item.name
        val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price)
        binding.tvPrice.text = getString(R.string.price_per_night, formatted)
        binding.tvDescription.text = item.description
    }

    private fun saveLastViewed(name: String) {
        val prefs = getSharedPreferences(
            getString(R.string.prefs_name),
            Context.MODE_PRIVATE
        )
        prefs.edit().putString(getString(R.string.prefs_key_last_viewed), name).apply()
    }

    @Suppress("DEPRECATION")
    private fun readRoomExtra(): RoomItem? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ROOM, RoomItem::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_ROOM)
        }
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

    companion object {
        const val EXTRA_ROOM = "extra_room"
    }
}
