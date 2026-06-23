package com.example.roommarket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.roommarket.databinding.ActivitySplashBinding

// 앱 시작시 2.5초 정도 나타나는 스플래시화면. 이후엔 메인화면 표시
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 2.5초 후에 메인화면으로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()    //뒤로가기 시엔 스플래시 안뜨도록
        }, SPLASH_DELAY_MS)
    }

    companion object {
        private const val SPLASH_DELAY_MS = 2500L   //스플래시 시간 2.5초
    }
}
