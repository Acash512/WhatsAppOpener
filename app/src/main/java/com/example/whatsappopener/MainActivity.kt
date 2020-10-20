package com.example.whatsappopener

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        var number = "0"
        
        if(intent.action == Intent.ACTION_PROCESS_TEXT) number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        
        if((number.length==10 && number.isDigitsOnly()) || (number.length==13 && number[0]=='+' && number.substring(1).isDigitsOnly())
        ){
            startWhatsApp(number)
        }else{
            Toast.makeText(this,"Invalid phone number",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun startWhatsApp(number: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.setPackage("com.whatsapp")
        val data = if(number.length==10){
            "+91$number"
        }else{
            number
        }

        i.data = Uri.parse("https://wa.me/$data")

        if(packageManager.resolveActivity(i,0)!=null){
            startActivity(i)
        }else{
            Toast.makeText(this,"Please install WhatsApp",Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}