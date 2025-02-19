package com.example.fragmentapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.Locale

private var TEMP = "param1"
private var MAIN = "param2"
private var WINDDEG = "param3"
private var WINDSPEED= "param4"
private var HUMIDITY= "param5"
class CurrentTaskFragment: Fragment() {

    private var temp: String? = null
    private var main: String? = null
    private var deg: String? = null
    private var speed: String? = null
    private var hum: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        temp = arguments?.getString(TEMP)
        main = arguments?.getString(MAIN)
        deg = arguments?.getString(WINDDEG)
        speed = arguments?.getString(WINDSPEED)
        hum = arguments?.getString(HUMIDITY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_task, container, false)
        view.setBackgroundColor(Color.BLUE)
        val currentLocale = Locale.getDefault().language
        var temptext:TextView = view.findViewById(R.id.temp)
        temptext.text = temp + " c"
        var img:ImageView = view.findViewById(R.id.weatherimage)
        when (main) {
            "Snow" -> img.setImageResource(R.drawable.snow)
            "Clouds" -> img.setImageResource(R.drawable.cloudy)
            "Clear" -> img.setImageResource(R.drawable.sun)
            else -> { // Note the block
                img.setImageResource(R.drawable.sun)
            }
        }
        var degimg:ImageView = view.findViewById(R.id.windimage)
        var speedtext:TextView = view.findViewById(R.id.speed)
        speedtext.text = speed + " м/c"
        if (deg!="Loading") {
            when (deg?.toInt()) {
                in 0..45 -> degimg.setRotation(0.0F)
                in 45..90 -> degimg.setRotation(45.0F)
                in 90..135 -> degimg.setRotation(90.0F)
                in 135..180 -> degimg.setRotation(135.0F)
                in 180..225 -> degimg.setRotation(180.0F)
                in 225..270 -> degimg.setRotation(215.0F)
                in 270..315 -> degimg.setRotation(270.0F)
                in 315..360 -> degimg.setRotation(315.0F)
                else -> {
                    degimg.setRotation(0.0F)
                }
            }
        }
        var humtext:TextView = view.findViewById(R.id.humid)
        humtext.text=hum
        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(temp: String, main: String,deg: String,speed: String,humid: String ) =
            CurrentTaskFragment().apply {
                arguments = Bundle()
                arguments?.putString(TEMP, temp)
                arguments?.putString(MAIN, main)
                arguments?.putString(WINDDEG, deg)
                arguments?.putString(WINDSPEED, speed)
                arguments?.putString(HUMIDITY, humid)
            }
    }
}
