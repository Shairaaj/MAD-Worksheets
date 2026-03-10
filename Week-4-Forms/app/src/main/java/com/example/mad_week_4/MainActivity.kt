package com.example.mad_week_4

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val nameXML = findViewById<EditText>(R.id.userNameET);
        val rollNoXML = findViewById<EditText>(R.id.rollNoET);
        val genderRadioGrp = findViewById<RadioGroup>(R.id.radioGroupGender);

        // 1. Reference the Spinner
        val spinner = findViewById<Spinner>(R.id.programmeSpinner)

        // 2. Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.departments_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // 3. Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // 4. Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val DOBXML = findViewById<EditText>(R.id.editTextDate);
        val button = findViewById<Button>(R.id.button);

        button.setOnClickListener {
            val selectedRadioBtnID = genderRadioGrp.checkedRadioButtonId;
            val selectedGender = if(selectedRadioBtnID != -1){
                findViewById<RadioButton>(selectedRadioBtnID).text;
            }
            else{
                "None Selected";
                Toast.makeText(this, "None Selected", Toast.LENGTH_SHORT).show()
            }
            val hobbyList = mutableListOf<String>()

            val cbSwimming = findViewById<CheckBox>(R.id.hobbiesCB1)
            val cbDancing = findViewById<CheckBox>(R.id.hobbiesCB2)
            val cbReading1 = findViewById<CheckBox>(R.id.hobbiesCB3)
            val cbReading2 = findViewById<CheckBox>(R.id.hobbiesCB4)

            if (cbSwimming.isChecked) hobbyList.add(cbSwimming.text.toString())
            if (cbDancing.isChecked) hobbyList.add(cbDancing.text.toString())
            if (cbReading1.isChecked) hobbyList.add(cbReading1.text.toString())
            if (cbReading2.isChecked) hobbyList.add(cbReading2.text.toString())
            val selectedDept = spinner.selectedItem.toString();
            // Join the list into a single string separated by commas
            val selectedHobbies = hobbyList.joinToString(", ")
            val intent = Intent(this, SecondActivity::class.java).apply {
                // Adding the data as "Extras" (Key-Value   pairs)
                putExtra("USER_NAME", nameXML.text.toString())
                putExtra("ROLL_NO", rollNoXML.text.toString())
                putExtra("GENDER", selectedGender.toString())
                putExtra("HOBBIES", selectedHobbies)
                putExtra("PROGRAMME", selectedDept)
                putExtra("DOB", DOBXML.text.toString())
            }
            startActivity(intent)
        }


    }
}