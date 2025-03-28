package com.example.myneighborly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val startButton: Button = findViewById(R.id.buttonStart)

        startButton.setOnClickListener {
            Log.d("SplashActivity", "Start button clicked")

            val currentUser = FirebaseAuth.getInstance().currentUser
/*
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {



 */
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            //}
        }
    }
}
