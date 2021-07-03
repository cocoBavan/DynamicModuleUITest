package com.bltech.dynoninstall

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DynamicOnInstall : AppCompatActivity() {

    var currentNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oninstall)

        findViewById<Button>(R.id.buttononinstall).setOnClickListener {
            currentNumber *= 2
            findViewById<TextView>(R.id.textView).text = "$currentNumber"
        }
    }
}