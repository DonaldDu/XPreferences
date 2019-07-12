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

        val settings: AppSettings = XPreferences.get(this) ?: AppSettings()//读取配置
        tv.text = "startDate ${settings.startDate}"

        settings.startDate = System.currentTimeMillis()
        XPreferences.put(this, settings)//存储配置

        tvStatic.setOnClickListener {
            val s: AppSettings = XPreferences.get(this, true) ?: AppSettings()//读取SD卡配置
            tv.text = "startDate ${s.startDate}"
            s.startDate = System.currentTimeMillis()
            XPreferences.put(this, s, true)//存储配置到SD卡
        }

        tvReq.setOnClickListener {
            requestFilePermission()
        }
    }
}
