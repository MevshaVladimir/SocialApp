package com.example.myproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class SignUpActivity : AppCompatActivity() {

    private val etEmail: EditText by lazy { findViewById(R.id.signUp_Email) }
    private val etPassword: EditText by lazy { findViewById(R.id.signUp_Password) }
    private val btnRegister: AppCompatButton by lazy { findViewById(R.id.signUp_register_button) }

    private var email : String = ""
    private var password : String = ""

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Получение экземпляра SharedPreferences
        myPreferences = MyPreferences(this)

        btnRegister.setOnClickListener {
            email = etEmail.text.toString().trim()
            password = etPassword.text.toString().trim()

            if (!isEmailValid(email)) {
                etEmail.error = getString(R.string.SignUp_EmailError)
                return@setOnClickListener
            } else if (!isPasswordValid(password)) {
                etPassword.error = getString(R.string.SignUp_PasswordError)
                return@setOnClickListener
            } else {
                Toast.makeText(this, getString(R.string.SignUp_ValidationCompleted), Toast.LENGTH_SHORT).show()
                myPreferences.saveData(email, password)
                startActivityMain()
            }
        }

        autologin()
    }

    private fun autologin() {
        val email = myPreferences.getEmail()
        val password = myPreferences.getPassword()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            startActivityMain()
        }
    }


    private fun isEmailValid(email: String): Boolean {
        val pattern = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
        return pattern.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() }
    }

    private fun startActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        val email = myPreferences.getEmail() ?: etEmail.text.toString().trim()
        intent.putExtra("email", email)
        startActivity(intent)
    }
}