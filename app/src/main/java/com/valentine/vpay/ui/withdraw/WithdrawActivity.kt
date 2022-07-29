package com.valentine.vpay.ui.withdraw

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.valentine.vpay.databinding.ActivityWithdrawBinding
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.TransferAndWithdrawBody
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WithdrawActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithdrawBinding
    private val model: WithdrawViewModel by viewModels {
        WithdrawFactory((application as VpayApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.withdrawToolbar.apply {
            title = "Withdraw"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                this@WithdrawActivity.onBackPressed()
            }
        }


        val phone = intent.getStringExtra(PHONE_EXTRA)!!
        binding.account.text = phone.toEditable()

        binding.btnWithdraw.setOnClickListener {
            it.hideSoftKeyboard()
            doWithdraw(phone)
        }

        binding.btnCancel.setOnClickListener {
            it.hideSoftKeyboard()
            binding.amount.text = "".toEditable()
        }
    }

    private fun doWithdraw(phone: String) {
        if (!TextUtils.isEmpty(binding.amount.text)) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val amount = binding.amount.text.toString().toDouble()
                    if (!TextUtils.isEmpty(binding.amount.text)) {
                        model.doWithdraw(TransferAndWithdrawBody(phone, amount))
                    }

                    this@WithdrawActivity.runOnUiThread {
                        Toast.makeText(this@WithdrawActivity, "Withdraw Successful",
                            Toast.LENGTH_LONG).show()
                    }
                    val history = History(phoneNumber = phone, type = "Withdraw", amount = amount)
                    model.addHistory(history)

                } catch (e: Exception) {
                    this@WithdrawActivity.runOnUiThread {
                        Toast.makeText(this@WithdrawActivity, "Insufficient fund",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

        } else {
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_LONG).show()
        }
    }

    private fun View.hideSoftKeyboard() {
        val imm = this@WithdrawActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }
}