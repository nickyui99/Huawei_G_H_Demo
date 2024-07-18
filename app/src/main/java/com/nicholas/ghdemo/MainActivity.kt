package com.nicholas.ghdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicholas.ghdemo.GmsHmsServicesUtils.GmsHmsAvailability

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.simpleName
    val GH = GmsHmsAvailability(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Checking signature
        checkSignature()

        //Handling G+H Logic, you can call GMS services and HMS services respectively on GMS devices and HMS devices
        //Error handling is recommended to prevent app failure
        if(GH.isHmsAvailable()) {
            //Logic for HMS
        } else if(
            GH.isGmsAvailable() &&
            GH.isGmsSignatureValid("com.android.vending")
            ){
            //Logic for GMS
        } else {
            //Handle error if no services available
            Log.e(TAG,"No mobile service available");
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkSignature() {
        Log.i(TAG, "GMS Availability: " + GmsHmsAvailability(this).isGmsAvailable())
        findViewById<TextView>(R.id.textView1).setText("GMS Availability: " + GmsHmsAvailability(this).isGmsAvailable())

        Log.i(TAG, "HMS Availability: " + GmsHmsAvailability(this).isHmsAvailable())
        findViewById<TextView>(R.id.textView2).setText("HMS Availability: " + GmsHmsAvailability(this).isHmsAvailable())

        Log.i(TAG, "Signature Validity: " + GmsHmsAvailability(this).isGmsSignatureValid("com.android.vending"))
        findViewById<TextView>(R.id.textView3).setText("Signature Validity: " + GmsHmsAvailability(this).isGmsSignatureValid("com.android.vending"))
    }
}