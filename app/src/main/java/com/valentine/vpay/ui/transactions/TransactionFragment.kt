package com.valentine.vpay.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentine.vpay.databinding.FragmentTransactionBinding
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val model: TransactionsViewModel by viewModels {
        TransactionsFactory((requireActivity().application as VpayApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val phone = requireActivity().intent.getStringExtra(PHONE_EXTRA)
        CoroutineScope(Dispatchers.IO).launch {
            val data = model.getTransactions().data.filter {
                it.phoneNumber == phone
            }
            requireActivity().runOnUiThread{
                if (data.isEmpty()) {
                    binding.noItem.visibility = View.VISIBLE
                    binding.transactionsList.visibility = View.INVISIBLE
                } else {
                    binding.noItem.visibility = View.INVISIBLE
                    binding.transactionsList.visibility = View.VISIBLE
                }
            }
        }

        phone?.let { displayTransactions(it) }

        return binding.root
    }

    private fun displayTransactions(phone: String) {
        val adapters = TransactionsAdapters()
        binding.transactionsList.apply {
            adapter = adapters
            layoutManager = LinearLayoutManager(requireContext())
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = model.getTransactions().data.filter {
                it.phoneNumber == phone
            }
            requireActivity().runOnUiThread {
                adapters.submitList(data)
            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}