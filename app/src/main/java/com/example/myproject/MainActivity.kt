package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myproject.common.Constants
import com.example.myproject.common.Constants.EMAIL_INTENT_KEY
import com.example.myproject.databinding.ActivityMainBinding
import com.example.myproject.databinding.ActivitySignUpBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserName()
    }

    private fun setUserName() {
        val email = intent.getStringExtra(EMAIL_INTENT_KEY)
        val parsedName = parseEmail(email.toString())
        binding.profileUsername.text = parsedName
    }

    private fun parseEmail(email: String): String {         //todo extract to object
        val parts = email.split("@")[0].split(".")
        val name = parts[0].replaceFirstChar { it.uppercaseChar() }
        val surname = if (parts.size > 1) parts[1].replaceFirstChar { it.uppercaseChar() } else ""
        return if (parts.size > 1) "$name $surname" else name
    }
}