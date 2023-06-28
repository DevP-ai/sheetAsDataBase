package com.dev.android.sheet.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.dev.android.sheet.R
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.databinding.ActivityOptionsBinding
import com.google.firebase.auth.FirebaseAuth

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!.findNavController()

        val popupMenu=PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navHost)

//        navHost.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
//            override fun onDestinationChanged(
//                controller: NavController,
//                destination: NavDestination,
//                arguments: Bundle?
//            ) {
//
//            }
//
//        })

//        this.supportActionBar!!.displayOptions=ActionBar.DISPLAY_SHOW_CUSTOM
//
//        supportActionBar!!.setDisplayShowCustomEnabled(true)
//        supportActionBar!!.setCustomView(R.layout.custom_bar)
//
//        var logout=supportActionBar!!.customView.findViewById<ImageView>(R.id.imgLogout)
//
//        logout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            startActivity(Intent(this,LogInActivity::class.java))
//            this.finish()
//            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
//        }



//        binding.enterData.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//
//        binding.readData.setOnClickListener {
//            startActivity(Intent(this, ReadActivity::class.java))
//        }
    }
}