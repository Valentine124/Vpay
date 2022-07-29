package com.valentine.vpay.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentine.vpay.databinding.FragmentHomeBinding
import com.valentine.vpay.main.PHONE_EXTRA
import com.valentine.vpay.main.VpayApplication
import com.valentine.vpay.ui.fund.FundActivity
import com.valentine.vpay.ui.history.HistoryAdapter
import com.valentine.vpay.ui.transfer.TransferActivity
import com.valentine.vpay.ui.withdraw.WithdrawActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val model: HomeViewModel by viewModels {
        HomeFactory((requireActivity().application as VpayApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val phone = requireActivity().intent.getStringExtra(PHONE_EXTRA)

        binding.accountNo.text = phone

        phone?.let {
            displayBalance(it)
            binding.cardFund.setClickListener(FundActivity::class.java, it)
            binding.cardTransfer.setClickListener(TransferActivity::class.java, it)
            binding.cardWithdraw.setClickListener(WithdrawActivity::class.java, it)
        }
        model.histories.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.lisHistory.visibility = View.INVISIBLE
                binding.noItem.visibility = View.VISIBLE
            } else {
                binding.lisHistory.visibility = View.VISIBLE
                binding.noItem.visibility = View.INVISIBLE
            }
        }

        displayHistory()


        return binding.root
    }

    private fun displayHistory() {
        val adapters = HistoryAdapter()
        binding.lisHistory.apply {
            adapter = adapters
            layoutManager = LinearLayoutManager(requireContext())
        }

        model.histories.observe(viewLifecycleOwner) {
            adapters.submitList(it.reversed())
        }
    }

    private fun displayBalance(phone: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = model.getAccounts().data
            for (acc in data) {
                if (acc.phoneNumber == phone) {
                    binding.balance.text = acc.balance.toString()
                    break
                }
            }
        }
    }

    private fun CardView.setClickListener(clazz: Class<*>, phone: String) {
        this.setOnClickListener {
            val intent = Intent(requireContext(), clazz)
            intent.putExtra(PHONE_EXTRA, phone)
            startActivity(intent)
        }
    }

    override fun onResume() {
        val phone = requireActivity().intent.getStringExtra(PHONE_EXTRA)!!
        displayBalance(phone)
        super.onResume()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}