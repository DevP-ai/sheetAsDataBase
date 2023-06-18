package com.dev.android.sheet.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dev.android.sheet.R
import com.dev.android.sheet.adapter.ReadAdapter
import com.dev.android.sheet.model.EmpDetails

class EmpDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_data)

        supportActionBar!!.hide()

    }
}
