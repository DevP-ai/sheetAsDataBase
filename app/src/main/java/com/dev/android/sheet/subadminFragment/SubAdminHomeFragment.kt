package com.dev.android.sheet.subadminFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.android.sheet.R
import com.dev.android.sheet.admin.MainActivity
import com.dev.android.sheet.admin.ReadActivity
import com.dev.android.sheet.databinding.FragmentSubAdminHomeBinding
import com.dev.android.sheet.databinding.FragmentSubAdminProfileBinding
import com.dev.android.sheet.subadmin.EnterDataActivity
import com.dev.android.sheet.subadmin.ReadDataActivity

class SubAdminHomeFragment : Fragment() {
 private lateinit var binding:FragmentSubAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSubAdminHomeBinding.inflate(layoutInflater)

        binding.subAdminEnterData.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
        }
        binding.subAdminReadData.setOnClickListener {
            startActivity(Intent(requireContext(), ReadActivity::class.java))
        }

        return binding.root
    }

    companion object {

    }
}