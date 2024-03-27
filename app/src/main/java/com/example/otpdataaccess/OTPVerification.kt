package com.example.otpdataaccess

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential

interface OTPVerification {
    fun startPhoneNumberVerification(phoneNumber: String? , activity: Activity)
    fun verifyPhoneNumberWithCode(code: String)
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)
}