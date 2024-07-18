package com.nicholas.ghdemo.GmsHmsServicesUtils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability
import com.nicholas.ghdemo.GmsHmsServicesUtils.SignatureConstant.GOOGLE_PLAY_STORE_SIGNATURE
import java.security.MessageDigest

class GmsHmsAvailability(private val context: Context) {
    val TAG = GmsHmsAvailability::class.simpleName;
    val PLAY_STORE_SIGNATURE = SignatureFormatter().hexStringToByteArray(GOOGLE_PLAY_STORE_SIGNATURE)

    fun isHmsAvailable(): Boolean {
        val hmsApiAvailability = HuaweiApiAvailability.getInstance()
        val resultCode = hmsApiAvailability.isHuaweiMobileServicesAvailable(context)
        return resultCode == com.huawei.hms.api.ConnectionResult.SUCCESS
    }

    fun isGmsAvailable(): Boolean {
        val gmsApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = gmsApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    fun isGmsSignatureValid(packageName: String): Boolean {
        try {
            val packageInfo: PackageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            val signatures: Array<Signature> = packageInfo.signatures;

            // Here "com.android.vending" is the package name of GMS Core.
            if ("com.android.vending".equals(packageName)) {
                // The signature of GMS Core is usually known and can be hardcoded or obtained from the server.
                // This assumes that the known GMS Core signatures are stored in an array of strings called Google_Signature.

                for(signature in signatures) {
                    if (!isSignatureValid(signature.toByteArray(), PLAY_STORE_SIGNATURE)) {
                        return false;
                    }
                }

                return true;
            } else {
                // For other applications, you can choose to compare the signature with that of GMS Core.
                // or verify the validity of the signature by other means.
                // ...
            }
        } catch (err: PackageManager.NameNotFoundException) {
            err.printStackTrace();
        }

        return false;
    }

    private fun isSignatureValid(appSignature: ByteArray, googleSignature: ByteArray) : Boolean {
        // Here you can compare the signatures using the corresponding method of the Signature object.
        // Or use a custom method to determine if the signatures match
        // For example, you can use MessageDigest to get the SHA-1 hash of the signature and then compare it.
        // We are using SHA-256 for verifying signature

        // Create a MessageDigest instance for SHA-256
        val digest = MessageDigest.getInstance("SHA-256")

        // Calculate the SHA-256 hash of the app signature
        val appSignatureHash = digest.digest(appSignature)

        // Compare the computed hash with the known Google signature hash
        return appSignatureHash.contentEquals(googleSignature)
    }

}