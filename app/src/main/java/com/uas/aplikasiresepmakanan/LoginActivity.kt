package com.uas.aplikasiresepmakanan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPrefManager: SharedPreferenceManager
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrefManager = SharedPreferenceManager(this)

        usernameField = findViewById(R.id.editTextUsername)
        passwordField = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerLink = findViewById(R.id.textRegister)

        loginButton.setOnClickListener {
            val inputUsername = usernameField.text.toString()
            val inputPassword = passwordField.text.toString()

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
            } else {
                if (sharedPrefManager.isUserExist(inputUsername, inputPassword)) {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
