package com.dev.android.sheet.subadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.dev.android.sheet.R
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.databinding.ActivityChooseBinding
import com.google.firebase.auth.FirebaseAuth

class ChooseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar!!.displayOptions=ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_bar)

        var logout=supportActionBar!!.customView.findViewById<ImageView>(R.id.imgLogout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LogInActivity::class.java))
            this.finish()
            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
        }


        binding.SubAdminReadData.setOnClickListener {
            startActivity(Intent(this,ReadDataActivity::class.java))
        }

        binding.SubAdminEnterData.setOnClickListener {
            startActivity(Intent(this,EnterDataActivity::class.java))
        }

    }
}