package com.dev.android.sheet.subadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.android.sheet.R

class ReadDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)

        title="User Data"
    }
}