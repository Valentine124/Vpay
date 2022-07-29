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
import com.valentine.vpay.databinding.ActivitySignInBinding
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val model: SignUpViewModel by viewModels {
        SignUpFactory((application as VpayApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signup.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            it.hideSoftKeyboard()
            signIn()
        }

        binding.linkText.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

    }

    private fun signIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = model.signUpUser(binding.phone.text.toString(), binding.password.text.toString())
            if (response != null) {
                this@SignInActivity.runOnUiThread {
                    Toast.makeText(this@SignInActivity, response.message, Toast.LENGTH_LONG).show()
                }
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                intent.putExtra(PHONE_EXTRA, binding.phone.text.toString())
                startActivity(intent)
                finish()
            } else {
                this@SignInActivity.runOnUiThread {
                    Toast.makeText(this@SignInActivity, "Sign up failed. Try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun View.hideSoftKeyboard() {
        val imm = this@SignInActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}