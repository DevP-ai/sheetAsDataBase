package com.dev.android.sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.dev.android.sheet.databinding.ActivityMainBinding
import com.dev.android.sheet.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title="Home"

        binding.enterData.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.readData.setOnClickListener {
            startActivity(Intent(this,ReadActivity::class.java))
        }
    }
}