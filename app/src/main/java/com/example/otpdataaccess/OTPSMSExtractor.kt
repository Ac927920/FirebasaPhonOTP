package com.example.otpdataaccess

import java.util.regex.Pattern

class OTPSMSExtractor :OTPExtractor {
    override fun extractOTPFromSMS(message: String): String? {
        val pattern = Pattern.compile("\\b\\d{6}\\b") // Pattern to match 6 digit OTP
        val matcher = pattern.matcher(message)
        return if (matcher.find()) {
            matcher.group() // Extracting the matched OTP
        } else {
            null // Return null if no match found
        }

    }

}