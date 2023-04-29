package com.example.dicodingstory.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dicodingstory.MainActivity
import com.example.dicodingstory.R
import com.example.dicodingstory.activity.register.RegisterActivity
import com.example.dicodingstory.api.RetrofitClient
import com.example.dicodingstory.data.LoginRequest
import com.example.dicodingstory.data.LoginResponse
import com.example.dicodingstory.preference.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var btnLogin: Button
    private lateinit var btnRegis: Button
    private lateinit var edLoginEmail: EditText
    private lateinit var edLoginPassword: EditText

    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin = findViewById(R.id.btn_login)
        btnRegis = findViewById(R.id.btn_regis)
        edLoginEmail = findViewById(R.id.ed_login_email)
        edLoginPassword = findViewById(R.id.ed_login_password)
        userPreferences = UserPreferences(this)
        if (userPreferences.isLoggedIn) {
            val token = userPreferences.token
            Log.d("TOKEN", "Token: $token")
        }

        initListeners()
        initObservers()

    }
    private fun initListeners() {
        btnLogin.setOnClickListener {
            val email = edLoginEmail.text.toString().trim()
            val password = edLoginPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initObservers() {
        viewModel.loginResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val loginResult = response.body()?.loginResult
                if (loginResult != null) {

                    userPreferences.isLoggedIn = true
                    userPreferences.token = loginResult.token


                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("LOGIN", "Login failed: invalid response")
                }
            } else {
                Log.d("LOGIN", "Login failed: ${response.message()}")
            }
        })
    }
}