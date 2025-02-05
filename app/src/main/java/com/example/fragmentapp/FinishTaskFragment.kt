package com.example.fragmentapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

private var TEMP = "param1"
private var MAIN = "param2"
private var WINDDEG = "param3"
private var WINDSPEED= "param4"
private var HUMIDITY= "param5"
class FinishTaskFragment: Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_finish_task, container, false)
        view.setBackgroundColor(Color.YELLOW)
        var temptext: TextView = view.findViewById(R.id.temp)
        temptext.text=temp+" c"
        var weather: TextView = view.findViewById(R.id.weather)
        weather.text=main
        var degtext: TextView = view.findViewById(R.id.winddeg)
        degtext.text = "Угол ветра:"+deg
        var speedtext: TextView = view.findViewById(R.id.speed)
        speedtext.text=speed+" м/c"
        var humtext:TextView = view.findViewById(R.id.humid)
        humtext.text=hum
        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(temp: String, main: String,deg: String,speed: String,humid:String ) =
            FinishTaskFragment().apply {
                arguments = Bundle()
                arguments?.putString(TEMP, temp)
                arguments?.putString(MAIN, main)
                arguments?.putString(WINDDEG, deg)
                arguments?.putString(WINDSPEED, speed)
                arguments?.putString(HUMIDITY, humid)
            }
    }
}