package com.dev.android.sheet.subadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.android.sheet.R

class EnterDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_data)

        title="Data Entry Form"
    }
}