package com.valentine.vpay.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentine.vpay.databinding.FragmentHistoryBinding
import com.valentine.vpay.main.VpayApplication

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val model: HistoryViewModel by viewModels {
        HistoryFactory((requireActivity().application as VpayApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        model.histories.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.historyList.visibility = View.INVISIBLE
                binding.noItem.visibility = View.VISIBLE
            } else {
                binding.historyList.visibility = View.VISIBLE
                binding.noItem.visibility = View.INVISIBLE
            }
        }

        displayHistory()
        return binding.root
    }

    private fun displayHistory() {
        val adapters = HistoryAdapter()
        binding.historyList.apply {
            adapter = adapters
            layoutManager = LinearLayoutManager(requireContext())
        }

        model.histories.observe(viewLifecycleOwner) {
            adapters.submitList(it.reversed())
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}