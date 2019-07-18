# XPreferences [![](https://jitpack.io/v/DonaldDu/XPreferences.svg)](https://jitpack.io/#DonaldDu/XPreferences)

Android SharedPreferences工具类，支持存储到SD卡，支持多用户。

```
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
```
