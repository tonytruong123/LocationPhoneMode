package com.example.features

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class FindCurrentLocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivy_find_current_location)

    }
    val intent1 = getIntent()
    @Override
     fun getText(view: View) {
        val street_name = findViewById<EditText>(R.id.street_name)
        val city_name = findViewById<EditText>(R.id.city_name)
        val state_name = findViewById<EditText>(R.id.state_name)
        val zip_name = findViewById<EditText>(R.id.zip_name)
        val country_name = findViewById<EditText>(R.id.country_name)

        var street_value: String = street_name.text.toString()
        var city_value: String = city_name.text.toString()
        var state_value: String = state_name.text.toString()
        var zip_value: String = zip_name.text.toString()
        var country_value: String = country_name.text.toString()
//        val result_name = findViewById<TextView>(R.id.result_name).apply { text= street_value + ", " + city_value + ", "+ state_value + " "+ zip_value + ", "+ country_value }

         // get the input from the location
        val location_name = findViewById<EditText>(R.id.location_name)
        var location_value: String = location_name.text.toString()

         // get the input from the result_name
//        val result_name = findViewById<TextView>(R.id.result_name)
//        var result_value: String = result_name.text.toString()
        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val hello123 = street_value + ", " + city_value + ", "+ state_value + " "+ zip_value + ", "+ country_value
        with (prefs.edit()){
            putString(location_value,hello123)
            apply()
        }

//        Toast.makeText(this, "Successfully save the data", Toast.LENGTH_SHORT ).show()
//        val key_name = findViewById<EditText>(R.id.key_name)
//        val key_value: String = key_name.text.toString()
        // the address of the location. EX: 1300 Crossing Pl, Austin, TX 78714, USA
        // make sure to use this for later
        val looking_value = prefs.getString(location_value,"N/A")
        val display_value: String = location_value + ": " + looking_value

        // radioGroup so only 1 radio button will be checked
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // display the button that is choose
        val vibrate_button =  radioGroup!!.findViewById<RadioButton>(R.id.vibrate_button)
        var vibrate_value: String = vibrate_button.text.toString()

        val ringer_button = radioGroup!!.findViewById<RadioButton>(R.id.ringer_button)
        var ringer_value: String = ringer_button.text.toString()

        // enter button
        val enter_button = findViewById<Button>(R.id.enter_button)


        enter_button.setOnClickListener {
            val intent = Intent(this@FindCurrentLocation, MainActivity::class.java)
            intent.putExtra("address", hello123)
//            intent.putExtra("vibrateValue", vibrate_value)
//            intent.putExtra("ringerValue",ringer_value)

            if ((street_value.trim() == "" || street_value == null)
                || (city_value.trim() == ""|| city_value == null)
                || (state_value.trim() == ""|| state_value == null)
                || (zip_value.trim() == ""|| zip_value == null)
                || (country_value.trim() == ""|| country_value == null) ||
                    ((street_value.trim() == "" || street_value == null) &&
                            (city_value.trim() == ""|| city_value == null)   &&
                            (state_value.trim() == ""|| state_value == null) &&
                            (zip_value.trim() == ""|| zip_value == null) &&
                            (country_value.trim() == ""|| country_value == null)
                            ))
                {
                Toast.makeText(this,"Missing input. Please fill in every input.", Toast.LENGTH_LONG).show()
            }else{
                if ( vibrate_button.isChecked) {
                    Toast.makeText(this, "$display_value. \n Mode: $vibrate_value", Toast.LENGTH_SHORT).show()
//        val output_data = findViewById<TextView>(R.id.output_data).apply { text= location_value + ": " + looking_value } }
                    val vibrate123 = "$display_value. \n Mode: $vibrate_value"
                    intent.putExtra("vibrate_value", vibrate123)
                }
                else  {
                    Toast.makeText(this,"$display_value. \n Mode: $ringer_value", Toast.LENGTH_SHORT).show()
//        val output_data = findViewById<TextView>(R.id.output_data).apply { text= location_value + ": " + looking_value }
                    val ringer123 = "$display_value. \n Mode: $ringer_value"
                    intent.putExtra("ringer_value", ringer123)
                }
                // will start the second page as long as every information are filled out
                intent.putExtra("enter_button", enter_button.getId())
                startActivity(intent)
            }
            }
    }
}


