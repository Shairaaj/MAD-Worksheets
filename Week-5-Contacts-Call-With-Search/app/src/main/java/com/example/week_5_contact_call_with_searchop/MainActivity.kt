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
import android.Manifest
import android.content.ContentResolver
import android.net.Uri
import android.widget.AdapterView
import android.widget.FilterQueryProvider
import android.widget.SearchView
import android.widget.SimpleCursorAdapter

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

        var search = findViewById<SearchView>(R.id.FilterSV)
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
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // This runs every time the user types a letter
                filterContacts(newText)
                return true
            }
        })

        l1.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            cur = parent.adapter.getItem(position) as Cursor
            i = cur.getString(cur.getColumnIndexOrThrow("data1")) as String
            start()
        }
    }
    private fun filterContacts(query: String?) {
        val adapter = l1.adapter as SimpleCursorAdapter
        adapter.filter.filter(query)
    }
    private fun start() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.CALL_PHONE }, 112
            )

        } else {
            c.data = Uri.parse("tel:"+i)
            startActivity(c)
        }
    }
    private fun readContact(){
        var from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID
        ).toTypedArray()
        var to = intArrayOf(android.R.id.text1, android.R.id.text2);
        var c: ContentResolver = contentResolver
        var s = c.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        var a = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, s, from, to, 0)
        l1.adapter = a
        // Tell the adapter how to perform a search query
        a.filterQueryProvider = FilterQueryProvider { constraint ->
            val selection = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
            val selectionArgs = arrayOf("%$constraint%") // The % signs mean "contains"

            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols,
                selection,
                selectionArgs,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
        }
    }
}