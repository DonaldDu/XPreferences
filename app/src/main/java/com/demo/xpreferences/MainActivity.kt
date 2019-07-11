package com.demo.xpreferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhy.xpreference.XPreferences
import com.dhy.xpreference.requestFilePermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data: AppSettings = XPreferences.get(this) ?: AppSettings()
        tv.text = "startDate ${data.startDate}"

        data.startDate = System.currentTimeMillis()
        XPreferences.put(this, data)

        tvStatic.setOnClickListener {
            val d: AppSettings = XPreferences.get(this, true) ?: AppSettings()

            tv.text = "startDate ${d.startDate}"

            d.startDate = System.currentTimeMillis()
            XPreferences.put(this, d, true)
        }

        tvReq.setOnClickListener {
            requestFilePermission()
        }
    }
}
