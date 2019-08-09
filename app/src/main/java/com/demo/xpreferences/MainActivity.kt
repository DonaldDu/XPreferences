package com.demo.xpreferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhy.xpreference.MultUserData
import com.dhy.xpreference.SingleInstance
import com.dhy.xpreference.XPreferences
import com.dhy.xpreference.requestFilePermission
import com.dhy.xpreference.util.StaticPref
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonInner.setOnClickListener {
            val settings: AppSettings = XPreferences.get(this)//读取配置
            settings.show()
            XPreferences.put(this, settings)//存储配置
        }

        buttonStatic.setOnClickListener {
            val settings: AppSettings = XPreferences.get(this, true)//读取SD卡配置
            settings.show()
            XPreferences.put(this, settings, true)//存储配置到SD卡
        }

        buttonSingleInstance.setOnClickListener {
            val settings: AppSettings = SingleInstance.get(this)
            settings.show()
            settings.save(this)
        }
        buttonReq.setOnClickListener {
            requestFilePermission()
        }
    }

    private fun AppSettings.show() {
        val new = System.currentTimeMillis()
        tv.text = "old $startDate, new $new"
        startDate = new
    }

    @StaticPref
    private class AppSettings : SingleInstance(), Serializable, MultUserData {
        var startDate: Long? = null
    }
}
