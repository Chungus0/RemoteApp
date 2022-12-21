package com.example.remoteapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.view.Gravity
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.remoteapp.ui.theme.RemoteAppTheme

const val REQUEST_CODE_SMS_PERMISSIONS = 1000
var ChatId:String? = null

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestSmsPermission()
        val sharedPref=getPreferences(Context.MODE_PRIVATE)
        val default=resources.getString(R.string.tg)
        ChatId= sharedPref.getString(default,"")
        setContent {
            RemoteAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RatMain()
                }
            }
        }
    }



    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_SMS_PERMISSIONS)
        }
    }

    private fun save(){
        val sharedChatId=getPreferences(Context.MODE_PRIVATE)
        val editor=sharedChatId.edit()
        editor.putString(getString(R.string.tg), ChatId)
        editor.apply()

        val toast = Toast.makeText(applicationContext,"Saved", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,140)
        toast.show()
    }

    @Composable
    fun RatMain() {
        var id by remember { mutableStateOf("") }
        var lab by remember {
            mutableStateOf("")
        }
        lab=texter(ChatId)
        Column() {
            Text(
                text = stringResource(R.string.enter_id),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))
            EnterIdField(
                value = id
            ) { id = it }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    ChatId=id
                    save()
                    lab=texter(ChatId)
                },
                modifier=Modifier.align(Alignment.CenterHorizontally)

            ){
                Text("Save", fontSize = 25.sp)
            }

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.current, lab),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
    fun texter(string: String?):String{
        if( string!=null){
            return string
        }else
            return "Not Valid"
    }

}


//@Entity(tableName="phone")
//class Phone(
//    @ColumnInfo(name = "id")
//    val id:String,
//    @ColumnInfo(name = "messages")
//    val messages:ArrayDeque<SMS>
//)
//
//class InventoryApplication : Application(){
//    val database: PhoneRoomDatabase by lazy { PhoneRoomDatabase.getDatabase(this) }
//}

@Composable
fun EnterIdField(
    value: String,
    onValueChanged: (String) -> Unit,
){
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

}

@Composable
fun RatMain() {
    var id by remember { mutableStateOf("") }
    Column() {
        Text(
            text = stringResource(R.string.enter_id),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EnterIdField(
            value = id
        ) { id = it }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
            },
            modifier=Modifier.align(Alignment.CenterHorizontally)

        ){
            Text("Save", fontSize = 25.sp)
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Your ID now is: $ChatId",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteAppTheme() {
        RatMain()
    }
}


