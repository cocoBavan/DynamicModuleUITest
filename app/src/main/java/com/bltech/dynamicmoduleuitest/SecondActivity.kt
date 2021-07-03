package com.bltech.dynamicmoduleuitest

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus


class SecondActivity  : AppCompatActivity() {

    companion object {
        const val REQUESTCODE = 1101
    }

    lateinit var progressDialog: ProgressBar
    lateinit var splitInstallManager: SplitInstallManager
    var sessionId = 0

    private val listener =
        SplitInstallStateUpdatedListener { splitInstallSessionState ->
            if (splitInstallSessionState.sessionId() == sessionId) {
                when (splitInstallSessionState.status()) {
                    SplitInstallSessionStatus.INSTALLED -> {
                        progressDialog.visibility = View.INVISIBLE
                        Log.e("hi", "Installed")
                        val newIntent = Intent()
                        newIntent.setClassName(BuildConfig.APPLICATION_ID, "com.bltech.dynondemaid.DynamicOnDemand")
                        startActivity(newIntent)
                    }
                    SplitInstallSessionStatus.DOWNLOADING -> {
                        val totalBytes = splitInstallSessionState.totalBytesToDownload()
                        val progress = splitInstallSessionState.bytesDownloaded()
                        Log.e("hi", "Downloading$totalBytes...$progress")
                        progressDialog.visibility = View.VISIBLE
                    }
                    SplitInstallSessionStatus.INSTALLING -> {
                        Log.e("hi", "Installing")
                    }
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                        try {
                            splitInstallManager.startConfirmationDialogForResult(
                                splitInstallSessionState,
                                this@SecondActivity, REQUESTCODE
                            )
                        } catch (ex: IntentSender.SendIntentException) {
                            // Request failed
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.button).setOnClickListener {
            val newIntent = Intent()
            newIntent.setClassName(BuildConfig.APPLICATION_ID, "com.bltech.dynoninstall.DynamicOnInstall")
            startActivity(newIntent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val request = SplitInstallRequest
                .newBuilder()
                .addModule("dynOnDemaid")
                .build()
            splitInstallManager.startInstall(request)
                .addOnFailureListener { e -> Log.e("hi", "Exception: $e") }
                .addOnSuccessListener { sessionId ->
                    this.sessionId = sessionId
                }
        }

        splitInstallManager = SplitInstallManagerFactory.create(this)
        progressDialog = findViewById(R.id.progressBar)
        splitInstallManager.registerListener(listener);

    }

    override fun onDestroy() {
        splitInstallManager.unregisterListener(listener)

        super.onDestroy()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                // User approved installation
            } else {
                // User approved installation
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}