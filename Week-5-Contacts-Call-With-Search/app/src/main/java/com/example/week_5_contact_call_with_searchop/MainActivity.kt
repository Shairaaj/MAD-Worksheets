package com.example.week_5_contact_call_with_searchop

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    private lateinit var l1: ListView
    private lateinit var c: Intent
    private lateinit var cur: Cursor
    private lateinit var i:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        l1 = findViewById<ListView>(R.id.ContactsListView)
        c = Intent(Intent.ACTION_CALL)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }


    }
}