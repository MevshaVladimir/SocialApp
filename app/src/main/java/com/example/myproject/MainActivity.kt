package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.getStringExtra("email")
        val parsedName = parseEmail(email.toString())

        val profileUsernameTextView = findViewById<TextView>(R.id.profile_Username)
        profileUsernameTextView.text = parsedName
    }

    fun startActivityMain(view: View) {}

    private fun parseEmail(email: String): String {
        val parts = email.split("@")[0].split(".")
        val name = parts[0].replaceFirstChar { it.uppercaseChar() }
        val surname = if (parts.size > 1) parts[1].replaceFirstChar { it.uppercaseChar() } else ""
        return if (parts.size > 1) "$name $surname" else name
    }
}