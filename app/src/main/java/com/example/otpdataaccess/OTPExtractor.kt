package com.example.otpdataaccess

interface OTPExtractor {
    fun extractOTPFromSMS(message: String): String?
}