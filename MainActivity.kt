package com.example.features

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    // var: when we create a variable and we don't know what to set the variable to yet, we will set it to null value => ERROR
    // so we will set lateinit var: at some point later, before we call the value, we will define the value
    // https://www.youtube.com/watch?v=G0iWHIDdMKs&ab_channel=DonnFelker-FreelancingforSoftwareDevelopers
    lateinit var lm: LocationManager
    lateinit var loc: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // our task is to print our location in the main screen
        // need permission from the user
        // Coarse location: only use Cell and Wifi => cost less battery
        // Fine location: Use GPS, Cell, Wifi => cost more battery
        // If the permission is not granted => no action
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
        // The array.of () function is an inbuilt function in JavaScript that creates a new array instance with variables present as the argument of the function.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                111
            )

        // Once the user granted the permission
        // set the variable lm as the location service
        lm = getSystemService(LOCATION_SERVICE) as LocationManager
        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

        // we need a notification for our work
        var nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // If we don't have the access granted from the user, we will ask the user to give us
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !nm.isNotificationPolicyAccessGranted) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }

        val main_button = findViewById<Button>(R.id.main_button)
        main_button.setOnClickListener {
            val intent1 = Intent(this@MainActivity, FindCurrentLocation::class.java)
            startActivity(intent1)
        }


        // we want to update our phone location at a certain time
        // Location Listerner has 4 methods, but we only use 1 method
        var ll = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                // we create the function with the p0 location
                reverseGeocode(p0)
                getText123()

            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }
        //.2f is float value
        // ll is location listener
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100.2f, ll)
    }

    private fun reverseGeocode(loc: Location?) {
        var gc = Geocoder(this, Locale.getDefault())
        // get the lat and long from the user
        var addresses = gc.getFromLocation(loc!!.latitude, loc.longitude, 2)
        // get the first address which is the lattitude
        var address = addresses[0]
        var textView2 = findViewById<TextView>(R.id.textView2)
        // addressline 0: first addressline
        // to get the state of our current location, we can use this
        // ${address.locality}, it will print Austin
        textView2.setText("${address.getAddressLine(0)}")
    }

    private fun getText123() {
        // get intent object, and data from intent
        val intent = getIntent()

        // maybe used in the future to display user input
//        if (intent.hasExtra("address")){
//            val hello123 = intent.getStringExtra("address")
//            var edt_input_value = findViewById<TextView>(R.id.edt_input_value).apply { text = hello123 }
//        }else{
//            var edt_input_value = findViewById<TextView>(R.id.edt_input_value).apply{text="No input"}
//        }

        // find the current location
        val textView2 = findViewById<TextView>(R.id.textView2)

        // result when finish comparing
        val textView5 = findViewById<TextView>(R.id.textView5)

        val hello123 = intent.getStringExtra("address")
        val vibrateButton = intent.getIntExtra("vibrateButton", 0)
        val vibrateValue = intent.getStringExtra("vibrateValue")

        val ringerButton = intent.getIntExtra("ringerButton", 0)
        val ringerValue = intent.getStringExtra("ringerValue")
//
//        if (vibrateButton == 0) {
//            val output_value = findViewById<TextView>(R.id.output_value).apply{text="Error"}
//        }else {
//            val output_value =
//                findViewById<TextView>(R.id.output_value).apply { text = "Address: $hello123. \nMode: $vibrateValue" }
//        }
//
//
//        if (ringerButton == 0){
//            val output_value = findViewById<TextView>(R.id.output_value).apply{text="Error"}
//        }else{
//            val output_value =
//                findViewById<TextView>(R.id.output_value).apply { text = "Address: $hello123. \nMode: $ringerValue" }
//        }

        val vibrate_value = intent.getStringExtra("vibrate_value")
        val ringer_value = intent.getStringExtra("ringer_value")

        if (vibrate_value == null) {
            val output_value =
                findViewById<TextView>(R.id.output_value).apply { text = "$ringer_value" }
        } else {
            val output_value =
                findViewById<TextView>(R.id.output_value).apply { text = "$vibrate_value" }
        }


//        val output_value =
//            findViewById<TextView>(R.id.output_value).apply { text = "$ringer_value" }
        val enter_button = intent.getIntExtra("enter_button", 0)

        if (enter_button == 0) {
            Toast.makeText(this, "Please hit the button on the top", Toast.LENGTH_SHORT).show()
            val output_value =
                findViewById<TextView>(R.id.output_value).apply { text = "Output" }
        } else {

            // input from textView2: current location
            var inputValue1: String = textView2.text.toString()
            // input from the user type in
//            val inputValue: String = edt_input_value.text.toString()
            if (hello123 == null || hello123.trim() == "") {
                Toast.makeText(
                    this,
                    "Please input data, edit text cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (inputValue1 == hello123) {
                    textView5.setText("Location matches")
                } else {
                    textView5.setText("Location does not match")
                }
            }
            var TrueOrFalse: String = textView5.text.toString()
            var True: String = "Location matches"
            var False: String = "Location does not match"
            // change the mode from normal to vibrate mode
            // we need a variable as audiomanager so we can change its mode
            var am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (TrueOrFalse === True && (vibrate_value !== null)) {
                am.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                Toast.makeText(applicationContext, "Vibrate Mode Set", Toast.LENGTH_SHORT).show()
            } else {
                if (TrueOrFalse === True && (ringer_value !== null)) {
                    am.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    Toast.makeText(applicationContext, "Normal Mode Set", Toast.LENGTH_SHORT).show()
                }
                if (TrueOrFalse === False)

                    Toast.makeText(applicationContext, "System will update the mode when the location matches", Toast.LENGTH_SHORT).show()
                    am.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    Toast.makeText(applicationContext, "Normal mode is automatically set up \n due to location does not match ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
