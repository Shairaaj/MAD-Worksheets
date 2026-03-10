package com.example.mad_week_4

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // 1. Get data from the Intent
        val name = intent.getStringExtra("USER_NAME")
        val rollNo = intent.getStringExtra("ROLL_NO")
        val gender = intent.getStringExtra("GENDER")
        val hobbies = intent.getStringExtra("HOBBIES")
        val programme = intent.getStringExtra("PROGRAMME")
        val dob = intent.getStringExtra("DOB");
        // 2. Find your display TextViews
        val resultTV = findViewById<TextView>(R.id.displayTextView)

        // 3. Display the data
        resultTV.text = """
        Name: $name
        Roll No: $rollNo
        Gender: $gender
        Hobbies: $hobbies
        Programme: $programme
        DOB: $dob
    """.trimIndent()
    }
}