package com.valentine.vpay.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.valentine.vpay.MainActivity
import com.valentine.vpay.databinding.ActivityLogInBinding
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private val model: LogInViewModel by viewModels {
        AuthFactory((application as VpayApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.logIn.setOnClickListener {
            binding.progressBar2.visibility = View.VISIBLE
            it.hideSoftKeyboard()
            logIn()
        }

        binding.linkText.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun logIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                model.logInUser(binding.phone.text.toString(), binding.password.text.toString())
            if (response != null) {
                this@LogInActivity.runOnUiThread {
                    Toast.makeText(this@LogInActivity, response.message, Toast.LENGTH_LONG)
                        .show()
                }
                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                intent.putExtra(PHONE_EXTRA, binding.phone.text.toString())
                startActivity(intent)
                finish()
            } else {
                this@LogInActivity.runOnUiThread {
                    Toast.makeText(
                        this@LogInActivity,
                        "Log in failed. Try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun View.hideSoftKeyboard() {
        val imm = this@LogInActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}