package com.example.otpdataaccess

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.concurrent.TimeUnit

class FirebaseAuthenticator : OTPVerification {
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var storedVerificationId :String
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Handle verification completion
            signInWithPhoneAuthCredential(credential)

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Handle verification failure
            Log.d("DATA1","OnFailed $e")


        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            storedVerificationId = verificationId
            Log.d("DATA1","STORE ID $storedVerificationId")

        }
    }

    override fun startPhoneNumberVerification(phoneNumber: String?,activity: Activity) {
        // Implementation for starting phone number verification
        Log.d("DATA1","Phone Number $phoneNumber")
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        try {
            val phoneNumberProto = phoneNumberUtil.parse(phoneNumber, "IN")
            val formattedPhoneNumber = phoneNumberUtil.format(phoneNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164)
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(formattedPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } catch (e: Exception) {
            // Handle parsing/formatting error
            Log.d("DATA1","Phone Verification $e")

        }
    }

    override fun verifyPhoneNumberWithCode(code: String) {
        // Implementation for verifying phone number with code
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        signInWithPhoneAuthCredential(credential)
        Log.d("DATA1","Verify credential $credential")

    }

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d("DATA1","Credential $credential")
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.d("DATA1","Login Successful")
                    val user = task.result?.user
                    Log.d("DATA1","User from Sign $user")
                } else {
                    // Handle sign-in failure
                }
            }

    }
}