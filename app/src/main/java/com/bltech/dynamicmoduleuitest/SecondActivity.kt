package com.bltech.dynamicmoduleuitest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.button).setOnClickListener {
            val newIntent = Intent()
            newIntent.setClassName(BuildConfig.APPLICATION_ID, "com.bltech.dynoninstall.DynamicOnInstall")
            startActivity(newIntent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}