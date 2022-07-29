package com.valentine.vpay.ui.fund

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.valentine.vpay.databinding.ActivityFundBinding
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.TransferAndWithdrawBody
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FundActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFundBinding
    private val model: FundViewModel by viewModels {
        FundFactory((application as VpayApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFundBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.fundToolbar.apply {
            title = "Fund"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                this@FundActivity.onBackPressed()
            }
        }


        val phone = intent.getStringExtra(PHONE_EXTRA)!!
        binding.account.text = phone.toEditable()

        binding.btnFund.setOnClickListener {
            it.hideSoftKeyboard()
            doTransfer(phone)
        }

        binding.btnCancel.setOnClickListener {
            it.hideSoftKeyboard()
            binding.amount.text = "".toEditable()
        }
    }

    private fun doTransfer(phone: String) {
        if (!TextUtils.isEmpty(binding.amount.text)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val amount = binding.amount.text.toString().toDouble()
                    if (!TextUtils.isEmpty(binding.amount.text)) {
                        model.doTransfer(TransferAndWithdrawBody(phone, amount))
                    }
                    this@FundActivity.runOnUiThread {
                        Toast.makeText(this@FundActivity, "Fund Successful", Toast.LENGTH_LONG)
                            .show()
                    }
                    val history = History(phoneNumber = phone, type = "Fund", amount = amount)
                    model.addHistory(history)
                } catch (e: Exception) {
                    this@FundActivity.runOnUiThread {
                        Toast.makeText(this@FundActivity, "An Error Occurred", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_LONG).show()
        }
    }

    private fun View.hideSoftKeyboard() {
        val imm = this@FundActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

}