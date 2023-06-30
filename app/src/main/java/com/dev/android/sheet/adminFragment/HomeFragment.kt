package com.dev.android.sheet.adminFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.android.sheet.R
import com.dev.android.sheet.admin.MainActivity
import com.dev.android.sheet.admin.ReadActivity
import com.dev.android.sheet.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)

        binding.adminEnterData.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
        }
        binding.adminReadData.setOnClickListener {
            startActivity(Intent(requireContext(),ReadActivity::class.java))
        }
        return binding.root
    }


}