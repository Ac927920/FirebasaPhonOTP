package com.example.otpdataaccess

import android.content.ContentResolver
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class OTPManage : AppCompatActivity() {
    private lateinit var otpInput: EditText
    private lateinit var submitButton: Button

    private var otpVerification= FirebaseAuthenticator()
    private  var otpExtractor: OTPExtractor = OTPSMSExtractor()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpmanage)
        otpInput = findViewById(R.id.OTP_v)
        submitButton = findViewById(R.id.submit_btn_v)


        val phoneNumber = intent.getStringExtra("phoneNumber")
        otpVerification.startPhoneNumberVerification(phoneNumber,this)
        getOtp()

        submitButton.setOnClickListener {
            val otpValue = otpInput.text.toString()
            if (otpValue.isNotEmpty()) {
                otpVerification.verifyPhoneNumberWithCode(otpValue)
            } else {
                Toast.makeText(this@OTPManage, "Enter OTP", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getOtp(){
        try {
            val cr: ContentResolver = applicationContext.contentResolver
            val st : Cursor? = cr.query(Telephony.Sms.CONTENT_URI,null,null,null,null)
            Log.d("DATA1","SMS $st")
            if (st!=null){
                if(st.moveToFirst()){
                    val body: String = st.getString(st.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    Log.d("DATA1","SMS $body")
                    val smsOTP : String? = otpExtractor.extractOTPFromSMS(body)
                    Log.d("DATA1","SMS $smsOTP")
                    otpInput.setText(smsOTP)
                    st.moveToNext()
                }
                st.close()
            }
        } catch (e : Exception){
            // Error
            Log.d("DATA1","Catch Error $e")
        }
    }

}

