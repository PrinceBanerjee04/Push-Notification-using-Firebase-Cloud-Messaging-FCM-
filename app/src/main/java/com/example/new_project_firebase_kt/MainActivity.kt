package com.example.new_project_firebase_kt

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var tokenTextView: TextView
    private lateinit var sendNotificationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tokenTextView = findViewById(R.id.tokenTextView)
        sendNotificationButton = findViewById(R.id.sendNotificationButton)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            val token = task.result
            if (token != null) {
                Log.d("FCM Token", "Token: $token")
                tokenTextView.text = "FCM Token: $token"
            } else {
                Log.e("FCM Token", "Token is null")
            }
        }

        sendNotificationButton.setOnClickListener {
            Log.d("DEBUG", "Send Notification button clicked!")
        }
    }
}
