package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myproject.common.Constants.EMAIL_INTENT_KEY
import com.example.myproject.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private var email : String = ""
    private var password : String = ""

    private lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        myPreferences = MyPreferences(this)
        autologin()

        setContentView(binding.root)
        setListeners()

    }

    private fun setListeners() {
        binding.signUpRegisterButton.setOnClickListener { onRegisterButtonClick() }
    }

    private fun onRegisterButtonClick() {

        with(binding) {
            email =  signUpEmail.text.toString().trim()
            password = signUpPassword.text.toString().trim()

            if (!isEmailValid(email)) {
                signUpEmail.error = getString(R.string.SignUp_EmailError)
            } else if (!isPasswordValid(password)) {
                signUpPassword.error = getString(R.string.SignUp_PasswordError)
            } else {
                Toast.makeText(this@SignUpActivity, getString(R.string.SignUp_ValidationCompleted), Toast.LENGTH_SHORT).show()      //todo extension method
                // showToast(getString(R.string.SignUp_ValidationCompleted))
                myPreferences.saveData(email, password)
                startActivityMain()
            }
        }



    }

    private fun autologin() {
        val email = myPreferences.getEmail()
        val password = myPreferences.getPassword()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            startActivityMain()
        }
    }


    private fun isEmailValid(email: String): Boolean {
        val pattern = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")        //todo google Patterns.EMAIL_ADDRESS.matcher
        return pattern.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() }
    }

    private fun startActivityMain() {
        val email = myPreferences.getEmail() ?: binding.signUpEmail.text.toString().trim()      //todo send email here as argument of method
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EMAIL_INTENT_KEY, email)
        startActivity(intent)
        finish()
    }
}