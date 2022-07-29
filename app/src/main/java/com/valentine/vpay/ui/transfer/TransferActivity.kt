package com.valentine.vpay.ui.transfer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.valentine.vpay.databinding.ActivityTransferBinding
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.TransferAndWithdrawBody
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    private val model: TransferViewModel by viewModels {
        TransferFactory((application as VpayApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.transferToolbar.apply {
            title = "Transfer"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                this@TransferActivity.onBackPressed()
            }
        }

        binding.btnTransfer.setOnClickListener {
            it.hideSoftKeyboard()
            doTransfer()
        }

        binding.btnCancel.setOnClickListener {
            it.hideSoftKeyboard()
            binding.account.text = "".toEditable()
            binding.amount.text = "".toEditable()
        }
    }

    private fun doTransfer() {
        if (!TextUtils.isEmpty(binding.amount.text) && !TextUtils.isEmpty(binding.account.text)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val amount = binding.amount.text.toString().toDouble()
                    val receiverPhone = binding.account.text.toString()
                    val phone = intent.getStringExtra(PHONE_EXTRA)!!
                    if (!TextUtils.isEmpty(binding.amount.text)) {
                        model.doTransfer(TransferAndWithdrawBody(receiverPhone, amount))
                        model.doWithdraw(TransferAndWithdrawBody(phone, amount))
                    }
                    this@TransferActivity.runOnUiThread {
                        Toast.makeText(this@TransferActivity, "Transfer Successful", Toast.LENGTH_LONG)
                            .show()
                    }
                    val history = History(phoneNumber = receiverPhone, type = "Transfer", amount = amount)
                    model.addHistory(history)
                } catch (e: Exception) {
                    this@TransferActivity.runOnUiThread {
                        Toast.makeText(this@TransferActivity, "An Error Occurred", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Enter a valid account and amount", Toast.LENGTH_LONG).show()
        }
    }

    private fun View.hideSoftKeyboard() {
        val imm = this@TransferActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

}